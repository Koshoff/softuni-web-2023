package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.ChangePasswordDTO;
import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.enums.ArticleStatus;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(Principal principal){
        ModelAndView modelAndView = new ModelAndView("profile");
        String username = principal.getName();
        UserDTO user = userService.findByUsername(username);
        if(user != null) {
            List<ArticleDTO> articles = articleService.findByAuthor(user);
            modelAndView.addObject("articles", articles);
        }


        return modelAndView;
    }

    @GetMapping("/profile/edit-article/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editArticle(@PathVariable("id") Long id, @RequestParam String  content) {
        ArticleDTO article = articleService.findById(id);
        if(article != null) {
            article.setContent(content);
            article.setArticleStatus(ArticleStatus.PENDING);
            articleService.updateArticle(article);
        }
        return new ModelAndView("profile");
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView changePassword(@ModelAttribute("changePasswordDTO") ChangePasswordDTO changePasswordDTO
            , RedirectAttributes redirectAttributes, Authentication authentication){
        String loggedInUsername = authentication.getName();
        boolean success = userService.changePassword(changePasswordDTO);
        if(success){
            redirectAttributes.addFlashAttribute("successMessage", "Password was changed successfully!");
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong.Please try again.");

        }
        return new ModelAndView("redirect:/profile");
    }

    @GetMapping("/banned")
    public ModelAndView bannedUsersEndPoint(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("banned");
    }





    //TODO : delete/post маппинг за триене на статия.


}
