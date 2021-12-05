package com.ohdoking.multi.web.controller.etc;

import com.ohdoking.multi.api.domain.Member;
import com.ohdoking.multi.api.service.etc.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/save")
    public void save(Member member) {
        Member mem = new Member();
        mem.setName(member.getName());
        memberService.save(mem);
    }

    @GetMapping
    public List<Member> memberList() {
        return memberService.memberList();
    }
}
