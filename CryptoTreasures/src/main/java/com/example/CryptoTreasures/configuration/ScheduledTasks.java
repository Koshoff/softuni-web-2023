package com.example.CryptoTreasures.configuration;

import com.example.CryptoTreasures.model.dto.ArticleDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.service.ArticleService;
import com.example.CryptoTreasures.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ScheduledTasks {

    private final static Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
    private final UserService userService;
    private final ArticleService articleService;

    private List<ArticleDTO> cachedArticles;

    public ScheduledTasks(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
        this.cachedArticles = new ArrayList<>();
    }
    @Scheduled(cron = "0 0 1 * * *")
    public void promoteUsers() {
        userService.promoteUsersToModerators();
    }


    @Scheduled(fixedRate = 86400000)
    public void updateMostLikedArticles() {
        LOGGER.info("Actualize most liked articles");
        this.cachedArticles = articleService.getMostLikedArticles();
    }

    public List<ArticleDTO> getCachedArticles() {
        return cachedArticles;
    }


}
