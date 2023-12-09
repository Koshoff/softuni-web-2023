package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.dto.CryptoCardDTO;
import com.example.CryptoTreasures.model.enums.ArticleStatus;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.CryptoCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ModeratorControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private CryptoCardService cryptoCardService;




    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    void showUnapprovedArticles() throws Exception{

        List<ArticleDTO> unapprovedArticles = Arrays.asList(new ArticleDTO(), new ArticleDTO());
        when(articleService.findByArticleStatus(ArticleStatus.PENDING)).thenReturn(unapprovedArticles);

        mockMvc.perform(get("/moderator/articles-approve"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles-approve"))
                .andExpect(model().attributeExists("unapprovedArticles"))
                .andExpect(model().attribute("unapprovedArticles", unapprovedArticles));
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    void approveArticle() throws Exception {

        Long articleId = 1L;
        ArticleDTO article = new ArticleDTO();
        article.setId(articleId);

        when(articleService.findById(articleId)).thenReturn(article);
        mockMvc.perform(post("/moderator/approve/" + articleId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/moderator/articles-approve"));

        verify(articleService).approveArticle(articleId);
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    void rejectArticle() throws Exception{

        Long articleId = 1L;
        String rejectionReason = "Reason";
        ArticleDTO article = new ArticleDTO();
        article.setId(articleId);

        when(articleService.findById(articleId)).thenReturn(article);
        mockMvc.perform(post("/moderator/reject/" + articleId)
                        .param("rejectionReason", rejectionReason))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/moderator/articles-approve"));

        verify(articleService).rejectArticle(articleId, rejectionReason);
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    void cryptoCard() throws Exception{
        mockMvc.perform(get("/moderator/make-crypto-card"))
                .andExpect(status().isOk())
                .andExpect(view().name("crypto-card"));
    }

    @Test
    @WithMockUser(roles = {"MODERATOR"})
    void testCryptoCardUnsuccessful()throws Exception {
        CryptoCardDTO validCryptoCardDTO = new CryptoCardDTO();

        mockMvc.perform(post("/moderator/make-crypto-card")
                        .param("fieldName1", "fieldValue1")  // Подавайте всички необходими параметри за валиден CryptoCardDTO
                        .param("fieldName2", "fieldValue2")
                        .flashAttr("cryptoCardDTO", validCryptoCardDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/moderator/crypto-card"));
    }
}