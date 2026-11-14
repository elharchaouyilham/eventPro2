
package com.example.eventPro2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Bienvenue dans EventPro2 !";
    }

    @GetMapping("/test")
    public String test() {
        return "API EventPro2 fonctionne correctement !";
    }
}
