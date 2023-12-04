package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.dto.OpinionDTO;
import com.example.CryptoTreasures.model.entity.User;

import java.util.List;

public interface CommentService {

   void saveComment(CommentDTO commentDTO);

   void savePostComment(OpinionDTO opinionDTO);

    List<CommentDTO> getCommentsByArticleId(Long articleId);
}
