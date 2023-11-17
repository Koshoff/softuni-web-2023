package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.UserRegistrationModel;
import com.example.CryptoTreasures.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
public class UserAuthentication {

    private final UserService userService;

    public UserAuthentication(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login-error")
    public ModelAndView loginError(){
        ModelAndView model = new ModelAndView("login");
        model.addObject("loginError", true);


        return  model;
    }



    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }



    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegistrationModel") UserRegistrationModel userRegistrationModel){
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegistrationModel") @Valid UserRegistrationModel userRegistrationModel,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("redirect:/register");
        }
        userService.register(userRegistrationModel);
        return  new ModelAndView("redirect:/login");
    }
}
