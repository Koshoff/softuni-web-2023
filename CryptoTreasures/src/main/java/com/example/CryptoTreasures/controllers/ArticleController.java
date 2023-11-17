package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Category;
import com.example.CryptoTreasures.model.AddArticleModel;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.CategoryRepository;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.CategoryService;
import com.example.CryptoTreasures.service.CommentService;
import com.example.CryptoTreasures.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


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
    public ModelAndView loadDefiArticles(@PageableDefault(size=2, sort = "id")Pageable pageable) {
        Page<Article> defiArticles = articleService.getArticlesByCategory("DeFi", pageable);
        ModelAndView model = new ModelAndView("defi");
        model.addObject("DeFiArticles", defiArticles);
        return model;
    }


    @GetMapping("/nft")
    public ModelAndView loadNFTArticles(@PageableDefault(size=10, sort="id")Pageable pageable) {
        Page<Article> nftArticles = articleService.getArticlesByCategory("NFT", pageable);
        ModelAndView model = new ModelAndView("nft");
        model.addObject("NFTArticles", nftArticles);
        return model;
    }

    @GetMapping("/cryptocurrencies")
    public ModelAndView loadCryptocurrencyArticles(@PageableDefault(size=10, sort="id")Pageable pageable) {
        Page<Article> cryptocurrencyArticles = articleService.getArticlesByCategory("Cryptocurrencies", pageable);
        ModelAndView model = new ModelAndView("cryptocurrencies");
        model.addObject("CryptocurrencyArticles", cryptocurrencyArticles);
        return model;
    }



    @GetMapping("/create-article")
    public ModelAndView create(@ModelAttribute("addArticleDTO") AddArticleModel addArticleModel){
        List<Category> categories = categoryService.findAll();
        ModelAndView model = new ModelAndView("create-article");
        model.addObject("categories", categories);
        return model;
    }

    @PostMapping("/create-article")
    public ModelAndView create(@ModelAttribute("addArticleDTO") @Valid AddArticleModel addArticleModel
                               , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("create-article");
        }
        articleService.saveArticle(addArticleModel);

        return new ModelAndView("about");
    }

    @PostMapping("/{id}/comment")
    public ModelAndView leaveComment(@PathVariable Long id, @ModelAttribute CommentDTO commentDTO, Principal principal) {
        Optional<User> author = userService.findByUsername(principal.getName());
        commentService.addCommentToArticle(id, commentDTO, author.orElse(null));
        return new ModelAndView("redirect:/article/" + id);
    }



//Трябва да се направи само Moderator да има достъп до тези методи.

    @GetMapping("/articles-approve")
    public ModelAndView showUnapprovedArticles() {
        List<Article> unapprovedArticles = articleService.findUnapprovedArticles();
        ModelAndView model = new ModelAndView("articles-approve");
        model.addObject("unapprovedArticles", unapprovedArticles);
        return model;
    }

    @PostMapping("/approve/{id}")
    public ModelAndView approveArticle(@PathVariable("id") Long articleId) {
        Article article = articleService.findById(articleId);
        articleService.approveArticle(article);
        return new ModelAndView("redirect:/article/articles-approve");
    }




}


