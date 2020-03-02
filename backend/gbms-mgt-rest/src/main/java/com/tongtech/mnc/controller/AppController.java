package com.tongtech.mnc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kernel")
public class AppController {

    @GetMapping(value = "/test")
    public String test(){
        return "hello";
    }
}
