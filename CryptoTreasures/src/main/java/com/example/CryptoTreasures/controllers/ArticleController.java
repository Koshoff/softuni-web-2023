package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.CategoryDTO;
import com.example.CryptoTreasures.model.entity.Category;
import com.example.CryptoTreasures.model.AddArticleModel;
import com.example.CryptoTreasures.model.enums.ArticleStatus;
import com.example.CryptoTreasures.repository.CategoryRepository;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.CategoryService;
import com.example.CryptoTreasures.service.CommentService;
import com.example.CryptoTreasures.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;



@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final UserService userService;

    public ArticleController(ArticleService articleService, CategoryRepository categoryRepository, CategoryService categoryService, CommentService commentService, UserService userService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/defi")
    public ModelAndView loadDefiArticles(@PageableDefault(size=4, sort = "id")Pageable pageable) {
        Page<ArticleDTO> defiArticles = articleService.findByArticleStatusAndCategoryName(ArticleStatus.APPROVED, "DeFi",pageable);
        ModelAndView model = new ModelAndView("defi");

        model.addObject("DeFiArticles", defiArticles);
        return model;
    }


    @GetMapping("/nft")
    public ModelAndView loadNFTArticles(@PageableDefault(size=4, sort="id")Pageable pageable) {
        Page<ArticleDTO> nftArticles = articleService.findByArticleStatusAndCategoryName(ArticleStatus.APPROVED, "NFT",pageable);
        ModelAndView model = new ModelAndView("nft");
        model.addObject("NFTArticles", nftArticles);
        return model;
    }

    @GetMapping("/cryptocurrencies")
    public ModelAndView loadCryptocurrencyArticles(@PageableDefault(size=4, sort="id")Pageable pageable) {
        Page<ArticleDTO> cryptocurrencyArticles = articleService.findByArticleStatusAndCategoryName(ArticleStatus.APPROVED, "Cryptocurrencies",pageable);
        ModelAndView model = new ModelAndView("cryptocurrencies");
        model.addObject("CryptocurrencyArticles", cryptocurrencyArticles);
        return model;
    }



    @GetMapping("/create-article")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView create(@ModelAttribute("addArticleDTO") AddArticleModel addArticleModel){
        List<CategoryDTO> categories = categoryService.findAll();
        ModelAndView model = new ModelAndView("create-article");
        model.addObject("categories", categories);
        return model;
    }

    @PostMapping("/create-article")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView create(@ModelAttribute("addArticleDTO") @Valid AddArticleModel addArticleModel
                               , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("create-article");
            modelAndView.addObject("addArticleDTO", addArticleModel);
            return modelAndView;
        }
        articleService.saveArticle(addArticleModel);
        return new ModelAndView("about");
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete-article/{articleId}")
    public ModelAndView deleteArticle(@RequestParam("articleId") Long articleId) {
        articleService.deleteArticle(articleId);
        return new ModelAndView("redirect:/profile");
    }
}


