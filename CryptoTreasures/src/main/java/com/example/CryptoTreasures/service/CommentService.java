package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.entity.User;

public interface CommentService {

    void addCommentToArticle(Long articleId, CommentDTO commentDTO, User author);
}
