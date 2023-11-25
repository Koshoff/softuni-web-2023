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
    public void saveArticle(AddArticleModel addArticleModel) {
        User author = userRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElseThrow();

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
    public ArticleDTO findById(Long id) {
        return modelMapper.map(articleRepository.findById(id).orElse(null), ArticleDTO.class);
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
    public void approveArticle(ArticleDTO articleDTO) {
        Article article = modelMapper.map(articleDTO, Article.class);
        article.setArticleStatus(ArticleStatus.APPROVED);
        article.setApproved(true);
        articleRepository.save(article);
    }

    @Override
    public void rejectArticle(Long articleId, String reasonMessage) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.setArticleStatus(ArticleStatus.REJECTED);
        article.setMessage(reasonMessage);
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
    public List<ArticleDTO> getMostLikedArticles() {
        return articleRepository.findMostLikedArticlesAscending()
                .stream()
                .map(article -> modelMapper.map(article, ArticleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteArticle(Long articleId) {
        if(articleRepository.existsById(articleId)){
            articleRepository.deleteById(articleId);

        }
        else{
            throw new EntityNotFoundException("Article ID " + articleId + " not found.");
        }
    }
}
