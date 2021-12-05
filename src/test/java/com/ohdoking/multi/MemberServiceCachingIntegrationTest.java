package com.ohdoking.multi;

import com.ohdoking.multi.api.domain.Member;
import com.ohdoking.multi.api.repository.MemberRepository;
import com.ohdoking.multi.api.service.MemberService;
import com.ohdoking.multi.config.RedisCacheConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import({RedisCacheConfig.class, MemberService.class})
@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
class MemberServiceCachingIntegrationTest {

    @MockBean
    private MemberRepository mockMemberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void givenRedisCaching_whenFindItemById_thenItemReturnedFromCache() {
        Long AN_ID = 0L;
        Member member = new Member();
        member.setId(AN_ID);
        member.setName("dokeun");
        given(mockMemberRepository.findById(AN_ID))
                .willReturn(Optional.of(member));

        Member itemCacheMiss = memberService.findOne(AN_ID).get();
        Member itemCacheHit = memberService.findOne(AN_ID).get();

        assertThat(itemCacheMiss).isEqualTo(member);
        assertThat(itemCacheHit).isEqualTo(member);

        verify(mockMemberRepository, times(1)).findById(AN_ID);
    }
}