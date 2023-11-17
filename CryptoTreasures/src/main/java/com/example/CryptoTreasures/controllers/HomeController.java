package com.example.CryptoTreasures.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/index")
    public ModelAndView homePage(){
        return new ModelAndView("index");
    }


    @GetMapping("/about")
    public ModelAndView aboutPage() {
        return  new ModelAndView("about");
    }
}
