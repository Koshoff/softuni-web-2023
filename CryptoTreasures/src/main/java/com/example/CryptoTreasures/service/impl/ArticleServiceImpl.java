package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.AddArticleModel;
import com.example.CryptoTreasures.model.enums.ArticleStatus;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {



    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public ArticleServiceImpl(
                              ArticleRepository articleRepository,
                              UserRepository userRepository,
                              ModelMapper modelMapper) {

        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void saveArticle(AddArticleModel addArticleModel) {
        User author = userRepository.findByUsername(SecurityUtils.getCurrentUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with username" + SecurityUtils.getCurrentUsername() + "not found"));

       Article article =new Article();
       article.setApproved(false);
       article.setArticleStatus(ArticleStatus.PENDING);
       article.setPublicationDate(LocalDate.now());
       article.setThumbnailUrl(addArticleModel.getThumbnailUrl());
       article.setContent(addArticleModel.getContent());
       article.setTitle(addArticleModel.getTitle());
       article.setAuthor(author);
       article.setCategory(addArticleModel.getCategory());

       articleRepository.save(article);


    }

    @Override
    public Page<ArticleDTO> getAllArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles.map(article -> modelMapper.map(article, ArticleDTO.class));

    }

    @Override
    public Page<ArticleDTO> getArticlesByCategory(String category, Pageable pageable) {
        Page<Article> articles = articleRepository.findByCategoryName(category, pageable);
        return articles.map(article -> modelMapper.map(article, ArticleDTO.class));
    }

    @Override
    public ArticleDTO findById(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException(
                "Article with id" + articleId + "not found"));
        return modelMapper.map(article, ArticleDTO.class);
    }

    public List<ArticleDTO> findUnapprovedArticles() {

        return articleRepository.findByIsApprovedFalse()
                .stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> findByArticleStatus(ArticleStatus articleStatus) {
        return articleRepository.findByArticleStatus(articleStatus)
                .stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public void approveArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException(
                "Article with id" + articleId + "not found"));
        article.setArticleStatus(ArticleStatus.APPROVED);
        article.setApproved(true);
        articleRepository.save(article);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public void rejectArticle(Long articleId, String reasonMessage) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException(
                "Article with id" + articleId + "not found"));
        article.setArticleStatus(ArticleStatus.REJECTED);
        article.setMessage(reasonMessage);
        articleRepository.save(article);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void updateArticle(ArticleDTO articleDTO) {
        Article article = modelMapper.map(articleDTO, Article.class);
        articleRepository.save(article);
    }



    @Override
    public List<ArticleDTO> findByAuthor(UserDTO author) {
        User user = userRepository.findByUsername(author.getUsername()).orElseThrow();
        return articleRepository.findByAuthor(user)
                .stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    @PreAuthorize("isAuthenticated()")
    public void deleteArticle(Long articleId) {
        if(articleRepository.existsById(articleId)){
            articleRepository.deleteById(articleId);

        }
        else{
            throw new EntityNotFoundException("Article ID " + articleId + " not found.");
        }
    }

    @Override
    public Page<ArticleDTO> findByArticleStatusAndCategoryName(ArticleStatus articleStatus, String categoryName, Pageable pageable) {
        Page<Article> articles = articleRepository.findByArticleStatusAndCategoryName(articleStatus, categoryName, pageable);
        return articles.map(article -> modelMapper.map(article, ArticleDTO.class));
    }

}
