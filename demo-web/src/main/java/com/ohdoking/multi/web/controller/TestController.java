package com.ohdoking.multi.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController("test")
public class TestController {
    @GetMapping("/hello")
    public String hello(WebRequest swr) {
        return "hello world";
    }

}
