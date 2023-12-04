package com.example.CryptoTreasures.repository;

import com.example.CryptoTreasures.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByArticleId(Long articleId);
    List<Comment> findByCommentatorId(Long commentatorId);
    List<Comment> findByPostId(Long postId);
}
