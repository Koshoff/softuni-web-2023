package com.example.CryptoTreasures.rest_controllers;

import com.example.CryptoTreasures.model.dto.CommentDTO;
import com.example.CryptoTreasures.model.dto.OpinionDTO;
import com.example.CryptoTreasures.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post-comment/{articleId}")
    @CrossOrigin(allowedHeaders = "Content-Type")
    public ResponseEntity<CommentDTO> postComment(@RequestBody CommentDTO commentDTO, @PathVariable("articleId") Long articleId) {

        commentService.saveComment(commentDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@RequestParam("articleId") Long articleId) {
        List<CommentDTO> comments = commentService.getCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/blog/{postId}/leave-opinion")
    @CrossOrigin(allowedHeaders = "Content-Type")
    public ResponseEntity<OpinionDTO> postOpinion(@RequestBody OpinionDTO opinionDTO, @PathVariable("postId") Long postId) {
        try{
            commentService.savePostComment(opinionDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }
}
