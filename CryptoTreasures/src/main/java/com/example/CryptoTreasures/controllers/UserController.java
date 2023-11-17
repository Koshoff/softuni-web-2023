package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.ChangePasswordModel;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ArticleService articleService;

    public UserController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/profile")
    public ModelAndView profile(Principal principal){
        ModelAndView modelAndView = new ModelAndView("profile");
        String username = principal.getName();
        User user = userService.findByUsername(username).orElse(null);
        if(user != null) {
            List<Article> articles = articleService.findByAuthor(user);
            modelAndView.addObject("articles", articles);
        }


        return modelAndView;
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@ModelAttribute("changePasswordModel")ChangePasswordModel changePasswordModel
            , RedirectAttributes redirectAttributes, Authentication authentication){
        String loggedInUsername = authentication.getName();
        boolean success = userService.changePassword(changePasswordModel);
        if(success){
            redirectAttributes.addFlashAttribute("successMessage", "Password was changed successfully!");
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong.Please try again.");

        }
        return new ModelAndView("redirect:/profile");
    }

    @GetMapping("/banned")
    public ModelAndView bannedUsersEndPoint(){
        return new ModelAndView("banned");
    }



    //TODO : delete/post маппинг за триене на статия.


}