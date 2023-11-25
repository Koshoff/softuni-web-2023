package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.entity.User;

public interface CommentService {

   void saveComment(CommentDTO commentDTO);
}
