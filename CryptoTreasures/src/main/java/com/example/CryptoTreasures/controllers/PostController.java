package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.AddArticleModel;
import com.example.CryptoTreasures.model.dto.PostDTO;
import com.example.CryptoTreasures.model.entity.Post;
import com.example.CryptoTreasures.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/blog")
    public ModelAndView showBlogPosts(@PageableDefault(size=5, sort = "id") Pageable pageable) {
        Page<PostDTO> posts = postService.getAllPosts(pageable);
        ModelAndView modelAndView = new ModelAndView("blog");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/create-post")
    public ModelAndView createPost(@ModelAttribute("postDTO") PostDTO postDTO) {
        return new ModelAndView("create-post");

    }

    @PostMapping("/create-post")
    public ModelAndView createPost(@ModelAttribute("postDTO") @Valid PostDTO postDTO
            , BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ModelAndView("create-post");
        }
        postService.savePost(postDTO);
        return new ModelAndView("blog");
    }


    @GetMapping("/blog/{id}")
    public ModelAndView showPostDetails(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("post-details");
        PostDTO post = postService.findByPostId(id);
        modelAndView.addObject("post", post);
        return modelAndView;
    }
}
