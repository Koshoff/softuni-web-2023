package com.example.CryptoTreasures.error_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotFoundController {

    @GetMapping("/not-found")
    public String testWhiteLabel() {
        throw new NullPointerException("Something went wrong");
    }
}
