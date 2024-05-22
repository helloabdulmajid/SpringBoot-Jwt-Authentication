package com.abdulmajid.springJwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo()
    {
        return "Hello from secured url";
    }


    @GetMapping("/admin_only")
    public String adminOnly()
    {
        return "Hello from Admin only url";
    }
}
