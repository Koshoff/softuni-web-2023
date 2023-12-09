package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Comment;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.CommentRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommentServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveComment() {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(2L);

        User user = new User();
        user.setUsername("user");

        Article article = new Article();
        article.setId(commentDTO.getArticleId());

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(articleRepository.findById(commentDTO.getArticleId())).thenReturn(Optional.of(article));

        commentService.saveComment(commentDTO);

        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void getCommentsByArticleId() {
    }
}