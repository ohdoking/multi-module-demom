package com.ohdoking.multi.web.controller;

import com.ohdoking.multi.api.domain.Member;
import com.ohdoking.multi.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/memSave")
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
