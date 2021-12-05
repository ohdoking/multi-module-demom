package com.ohdoking.multi.web.controller;

import com.ohdoking.multi.api.domain.Member;
import com.ohdoking.multi.api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping(value = "/mem_save")
    public void save(Member member) {
        Member mem = new Member();
        mem.setName(member.getName());
        memberService.save(mem);
    }

    @GetMapping("members")
    public List<Member> memberList() {
        return memberService.memberList();
    }
}
