package com.ohdoking.multi.web.controller.streaming;

import com.ohdoking.multi.api.domain.Match;
import com.ohdoking.multi.api.service.stream.LiveStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LiveStreamController {

    private final LiveStreamService liveStreamService;

    @GetMapping("/match/{id}")
    public Mono<Match> getMatchById(@PathVariable("id") Long id) {
        return liveStreamService.findMatchById(id);
    }

    @PostMapping("/match")
    public Mono<String> saveMatchDetails(@RequestBody Match match) {
        return liveStreamService.saveMatchDetails(match);
    }

    @GetMapping(value = "/match/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Match>> matchStream() {
        return liveStreamService.getStreamEvent(item -> item != null);
    }

    @GetMapping(value = "/match/{id}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Match>> matchStream(@PathVariable("id") Long id) {
        return liveStreamService.getStreamEvent(item -> item != null && item.data().getMatchId().equals(id));
    }
}