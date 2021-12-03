package com.ohdoking.multi.api.service;

import com.ohdoking.multi.api.domain.Member;
import com.ohdoking.multi.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    @CachePut(value = "member")
    public long save(Member member) {
        return memberRepository.save(member).getId();
    }

    @Cacheable(value = "member")
    public Optional<Member> findOne(long id) {
        return memberRepository.findById(id);
    }

    @CachePut(value = "member")
    public Optional<Member> update(Member member) {
        return Optional.of(memberRepository.save(member));
    }

    @CacheEvict(value = "member")
    public void delete(long id) {
        memberRepository.deleteById(id);
    }

    public List<Member> memberList() {
        return memberRepository.findAll();
    }
}