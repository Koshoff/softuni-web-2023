package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.PostDTO;
import com.example.CryptoTreasures.model.entity.Post;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.PostRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.PostService;
import com.example.CryptoTreasures.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(post -> modelMapper.map(post, PostDTO.class));
    }

    @Override
    public List<Post> post() {
        return postRepository.findAll();
    }

    @Override
    public void savePost(PostDTO postDTO) {
        User author = userRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElseThrow(() -> new EntityNotFoundException(
                "User with username" + SecurityUtils.getCurrentUsername() + "not found"));
        Post post = new Post();
        post.setAuthor(author);
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setDateCreated(LocalDate.now());
        postRepository.save(post);
    }

    @Override
    public PostDTO findByPostId(Long id) {
        return modelMapper.map(postRepository.findById(id), PostDTO.class);
    }


}
