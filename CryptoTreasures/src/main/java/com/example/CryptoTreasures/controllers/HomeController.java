package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.configuration.ScheduledTasks;
import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private final ArticleService articleService;

    private final ScheduledTasks scheduledTasks;

    public HomeController(ArticleService articleService, ScheduledTasks scheduledTasks) {
        this.articleService = articleService;
        this.scheduledTasks = scheduledTasks;
    }

    @GetMapping("/index")
    public ModelAndView homePage(){
        List<ArticleDTO> mostLikedArticles = scheduledTasks.getCachedArticles();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("mostLikedArticles", mostLikedArticles);

        return modelAndView;
    }


    @GetMapping("/about")
    public ModelAndView aboutPage() {
        return  new ModelAndView("about");
    }
}
