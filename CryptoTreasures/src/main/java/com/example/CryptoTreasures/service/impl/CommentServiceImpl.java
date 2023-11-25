package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Comment;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.CommentRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;


    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }


    @Override
    public void saveComment(CommentDTO commentDTO) {
        User user = userRepository.findByUsername(commentDTO.getAuthorName()).orElse(null);
        Article article = articleRepository.findById(commentDTO.getArticleId()).orElse(null);
        Comment comment = new Comment();
        comment.setCommentator(user);
        comment.setContent(commentDTO.getContent());
        comment.setPublicationDate(commentDTO.getCreateDate());
        comment.setArticle(article);
        commentRepository.save(comment);

    }
}
