package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.AddArticleModel;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.util.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveArticle(AddArticleModel addArticleModel) {

       Article article = modelMapper.map(addArticleModel, Article.class);
       articleRepository.save(article);

    }

    @Override
    public Page<Article> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);

    }

    @Override
    public Page<Article> getArticlesByCategory(String category, Pageable pageable) {
        return articleRepository.findByCategoryName(category, pageable);
    }

    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    private  ArticleDTO mapBack(Article article){
        List<CommentDTO> commentDTO = article.getComments().stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle(articleDTO.getTitle());
        articleDTO.setAuthorName(article.getAuthor().getUsername());
        articleDTO.setPublicationDate(article.getPublicationDate());
        articleDTO.setApproved(article.getApproved());
        articleDTO.setComments(commentDTO);
        articleDTO.setContent(article.getContent());
        return articleDTO;
    }



    private Article map(AddArticleModel addArticleModel){
        User author = userRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        Article article = new Article();
        if(author != null){
            article.setAuthor(author);
            article.setTitle(addArticleModel.getTitle());
            article.setCategories(addArticleModel.getCategory());
            article.setContent(addArticleModel.getContent());
            article.setPublicationDate(LocalDate.now());
            article.setThumbnailUrl(addArticleModel.getThumbnailUrl());
            article.setApproved(false);

        }

        return article;
    }


    public List<Article> findUnapprovedArticles() {
        return articleRepository.findByIsApprovedFalse();
    }

    @Override
    public void approveArticle(Article article) {
        article.setApproved(true);
        articleRepository.save(article);
    }

    @Override
    public List<Article> findByAuthor(User author) {
        return articleRepository.findByAuthor(author);
    }
}
