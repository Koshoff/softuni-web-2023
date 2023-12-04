package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.dto.PostDTO;
import com.example.CryptoTreasures.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {


    Page<PostDTO> getAllPosts(Pageable pageable);

    List<Post> post();

    void savePost(PostDTO postDTO);

    PostDTO findByPostId(Long id);
}
