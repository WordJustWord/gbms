package com.pigmo.gbms.controller.base;

import com.pigmo.gbms.utils.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class Login {

    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseResult.message("测试测试").ok(null);
    }
}
