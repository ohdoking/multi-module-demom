package com.ohdoking.multi.api.service.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.ohdoking.multi.api.domain.Match;
import com.ohdoking.multi.config.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveStreamService {

    private final ReactiveRedisTemplate<String, Match> matchReactiveRedisTemplate;
    private final KafkaSender<String, String> kafkaSender;
    private final KafkaService kafkaService;

    @Value("${kafka.livescore.topic}")
    private String topicName;

    private ReactiveHashOperations<String, String, Match> matchReactiveHashOperations() {
        return matchReactiveRedisTemplate.opsForHash();
    }

    public Mono<Match> findMatchById(Long id) {
        return matchReactiveHashOperations().get("matches", id.toString());
    }

    public Mono<String> saveMatchDetails(Match match) {
        final String matchStr;
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            matchStr = objectMapper.writeValueAsString(match);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        final SenderRecord<String, String, Long> senderRecord =
                SenderRecord.create(new ProducerRecord<>(topicName, matchStr), match.getMatchId());


        return matchReactiveHashOperations().put("matches", match.getMatchId().toString(), match)
                .then(
                        kafkaSender.send(Mono.just(senderRecord))
                                .next()
                                .doOnNext(longSenderResult -> System.out.println(longSenderResult.recordMetadata()))
                                .map(longSenderResult -> true)
                )
                /*
                .doOnNext(hashOperationResult -> {
                    SenderRecord<String, String, Long> senderRecord =
                            SenderRecord.create(new ProducerRecord<>(topicName, matchStr), match.getMatchId());
                    kafkaSender.send(Mono.just(senderRecord))
                            .next()
                            .doOnNext(longSenderResult -> System.out.println(longSenderResult.recordMetadata()))
                            .map(any -> "succsess")
                            .subscribe()
                    ;
                })
                */
                .map(hashOperationResult -> hashOperationResult ? "OK" : "NOK")
                .onErrorResume(throwable -> Mono.just("EXCEPTION : " + throwable.getMessage()));
    }

    public Flux<ServerSentEvent<Match>> getStreamEvent(Function<ServerSentEvent<Match>, Boolean> filter){
        return kafkaService.getEventPublisher()
                .doOnNext(stringServerSentEvent -> log.info(stringServerSentEvent.data()))
                .map(stringServerSentEvent -> {
                    ObjectMapper objectMapper = new ObjectMapper();

                    Match match = null;
                    try {
                        match = objectMapper.readValue(stringServerSentEvent.data(), Match.class);
                    } catch (Exception ex) {
                        log.warn("can't convert to Match, reason : %s", ex.getMessage());
                        return null;
                    }

                    return ServerSentEvent.builder(match).build();
                })
                .filter(matchServerSentEvent -> filter.apply(matchServerSentEvent))
                .onErrorContinue((throwable, o) -> log.error(throwable.getMessage()));
    }
}