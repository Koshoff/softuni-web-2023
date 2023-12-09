package com.example.CryptoTreasures.controllers;

import com.example.CryptoTreasures.service.CategoryService;
import com.example.CryptoTreasures.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CategoryService categoryService;



    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testAdminPanel() throws Exception{
        mockMvc.perform(get("/admin/admin-panel"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-panel"))
                .andExpect(model().attributeExists("users", "moderators", "bannedUsers"));
    }



    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testBanUser() throws Exception{
        Long userId = 1L;

        mockMvc.perform(post("/admin/ban-user").param("id", userId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/admin-panel"));

        verify(userService).banUser(userId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testUnbanUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(post("/admin/unban-user").param("id", userId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/admin-panel"));

        verify(userService).unbanUser(userId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testMakeModerator() throws Exception{
        Long userId = 1L;

        mockMvc.perform(post("/admin/make-moderator").param("id", userId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/admin-panel"));

        verify(userService).makeModerator(userId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testRemoveModerator() throws Exception {
        Long userId = 1L;

        mockMvc.perform(post("/admin/remove-moderator").param("id", userId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/admin-panel"));

        verify(userService).removeModerator(userId);
    }
}