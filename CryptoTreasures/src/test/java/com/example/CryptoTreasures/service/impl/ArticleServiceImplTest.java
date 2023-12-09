package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.AddArticleModel;
import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.enums.ArticleStatus;
import com.example.CryptoTreasures.repository.ArticleRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "user")
    void saveArticle() {
        AddArticleModel addArticleModel = new AddArticleModel();
        // Сетъп на addArticleModel с необходимите данни

        User author = new User();
        author.setUsername("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(author));
        articleService.saveArticle(addArticleModel);
        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void getAllArticles() {
    }

    @Test
    void getArticlesByCategory() {
    }

    @Test
    void findById() {
        Long articleId = 1L;
        User testUser = new User();
        testUser.setUsername("testUser");
        Article testArticle = new Article();
        testArticle.setAuthor(testUser);
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(testArticle));
        ArticleDTO expectedDTO = new ArticleDTO();
        expectedDTO.setContent(testArticle.getContent());
        expectedDTO.setTitle(testArticle.getTitle());
        expectedDTO.setAuthorName(testArticle.getAuthor().getUsername());


        Assertions.assertEquals(testArticle.getTitle(), expectedDTO.getTitle());
        Assertions.assertEquals(testArticle.getContent(), expectedDTO.getContent());
        Assertions.assertEquals(testArticle.getAuthor().getUsername(), expectedDTO.getAuthorName());

    }

    @Test
    void findUnapprovedArticles() {
        List<Article> unapprovedArticles = List.of(new Article());

        when(articleRepository.findByIsApprovedFalse()).thenReturn(unapprovedArticles);

        ArticleDTO expectedDTO = new ArticleDTO(); // Създайте очаквани ArticleDTO обекти според нуждите ви
        when(modelMapper.map(any(Article.class), eq(ArticleDTO.class))).thenReturn(expectedDTO);

        // Извикване на метода, който тествате
        List<ArticleDTO> result = articleService.findUnapprovedArticles();

        // Проверка на резултата
        assertEquals(1, result.size());
    }

    @Test
    void findByArticleStatus() {
        // Подготовка на данни за теста
        ArticleStatus status = ArticleStatus.PENDING;
        List<Article> articles = List.of(new Article()); // Подготовете списък с обекти Article според вашите нужди
        when(articleRepository.findByArticleStatus(status)).thenReturn(articles);

        ArticleDTO expectedDTO = new ArticleDTO(); // Подготовете очакваните DTO обекти според вашите нужди
        when(modelMapper.map(any(Article.class), eq(ArticleDTO.class))).thenReturn(expectedDTO);

        // Извикване на метода, който тествате
        List<ArticleDTO> result = articleService.findByArticleStatus(status);

        // Проверка на резултата
        assertEquals(1, result.size());
    }

    @Test
    @WithMockUser(username="user", roles={"MODERATOR"})
    void approveArticle() {

        Long articleId = 1L;

        Article article = new Article(); // Настройте article според нуждите на вашия тест

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        articleService.approveArticle(articleId);

        verify(articleRepository).save(article);
        assertEquals(ArticleStatus.APPROVED, article.getArticleStatus());

    }

    @Test
    @WithMockUser(username="user", roles={"MODERATOR"})
    void rejectArticle() {

        Long articleId = 1L;
        String reasonMessage = "Some rejection reason";
        Article article = new Article(); // Настройте article според нуждите на вашия тест

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        articleService.rejectArticle(articleId, reasonMessage);

        verify(articleRepository).save(article);
        assertEquals(ArticleStatus.REJECTED, article.getArticleStatus());
        assertEquals(reasonMessage, article.getMessage());
    }



    @Test
    void findByAuthor() {

        User author = new User();
        author.setUsername("testuser");

        UserDTO authorDTO = new UserDTO();
        authorDTO.setUsername("testuser");

        List<Article> articles = Arrays.asList(
                new Article(),
                new Article()
        );


        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(author));
        when(articleRepository.findByAuthor(author)).thenReturn(articles);


        List<ArticleDTO> result = articleService.findByAuthor(authorDTO);


        assertEquals(2, result.size());

    }


    @Test
    void deleteExistingArticle() {

        Long articleId = 1L;
        when(articleRepository.existsById(articleId)).thenReturn(true);
        articleService.deleteArticle(articleId);
        verify(articleRepository).deleteById(articleId);
    }

    @Test
    void deleteArticleWhenArticleDoesNotExist() {
        Long articleId = 1L;

        when(articleRepository.existsById(articleId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            articleService.deleteArticle(articleId);
        });

        verify(articleRepository, never()).deleteById(articleId);
    }
}