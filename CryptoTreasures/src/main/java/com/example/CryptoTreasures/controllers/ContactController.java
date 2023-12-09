package com.example.CryptoTreasures.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {


    @GetMapping("/contact-us")
    public ModelAndView contactForm() {
        return new ModelAndView("contact-us");
    }

    @GetMapping("/create-post")
    public ModelAndView createPost() {
        return new ModelAndView("contact-us");
    }

    @GetMapping("/blog")
    public ModelAndView blog() {
        return new ModelAndView("contact-us");
    }

}
