package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.dto.OpinionDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.Comment;
import com.example.CryptoTreasures.model.entity.Post;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.CommentRepository;
import com.example.CryptoTreasures.repository.PostRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.CommentService;
import com.example.CryptoTreasures.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    private final PostRepository postRepository;


    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;

        this.postRepository = postRepository;
    }


    @Override
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
    public void savePostComment(OpinionDTO opinionDTO) {
        Post post = postRepository.findById(opinionDTO.getId()).orElse(null);
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setPublicationDate(LocalDate.now());
        comment.setContent(opinionDTO.getContent());
        comment.setCommentator(user);
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
        commentDTO.setAuthorId(comment.getCommentator().getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreateDate(comment.getPublicationDate());
        return commentDTO;

    }


}
