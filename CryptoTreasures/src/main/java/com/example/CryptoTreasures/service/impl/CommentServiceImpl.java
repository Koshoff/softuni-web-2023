package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Comment;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.CommentRepository;
import com.example.CryptoTreasures.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }


    @Override
    public void addCommentToArticle(Long articleId, CommentDTO commentDTO, User author) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() ->  new EntityNotFoundException("Article not found"));
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setContent(commentDTO.getContent());
        comment.setPublicationDate(LocalDate.now());

        commentRepository.save(comment);
    }


}
