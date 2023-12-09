package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.dto.OpinionDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Comment;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.CommentRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.CommentService;
import com.example.CryptoTreasures.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    @PreAuthorize("isAuthenticated()")
    public void saveComment(CommentDTO commentDTO) {
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        Article article = articleRepository.findById(commentDTO.getArticleId()).orElse(null);
        Comment comment = new Comment();
        comment.setCommentator(user);
        comment.setContent(commentDTO.getContent());
        comment.setPublicationDate(LocalDate.now());
        comment.setArticle(article);
        commentRepository.save(comment);

    }



    @Override
    public List<CommentDTO> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(CommentServiceImpl::from)
                .collect(Collectors.toList());
    }


    private static CommentDTO from(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setArticleId(comment.getArticle().getId());
        commentDTO.setId(comment.getId());
        commentDTO.setAuthorName(comment.getCommentator().getUsername());
        commentDTO.setAuthorId(comment.getCommentator().getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreateDate(comment.getPublicationDate());
        return commentDTO;

    }


}
