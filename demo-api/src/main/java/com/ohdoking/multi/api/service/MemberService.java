package com.ohdoking.multi.api.service;

import com.ohdoking.multi.api.domain.Member;
import com.ohdoking.multi.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
    public long save(Member member) {
        return memberRepository.save(member).getId();
    }

    public Optional<Member> findOne(long id) {
        return memberRepository.findById(id);
    }

    public List<Member> memberList() {
        return memberRepository.findAll();
    }
}