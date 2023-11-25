package com.example.CryptoTreasures.restcontrollers;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post-comment/{articleId}")
    public ResponseEntity<CommentDTO> postComment(@PathVariable Long articleId, @RequestBody CommentDTO commentDTO, Principal principal) {
        commentService.saveComment(commentDTO);
        return ResponseEntity.ok().build();
    }
}
