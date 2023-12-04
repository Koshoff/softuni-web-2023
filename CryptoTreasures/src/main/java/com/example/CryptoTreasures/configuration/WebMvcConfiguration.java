package com.example.CryptoTreasures.configuration;

import com.example.CryptoTreasures.Interceptors.BanCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final BanCheckInterceptor banCheckInterceptor;

    public WebMvcConfiguration(BanCheckInterceptor banCheckInterceptor) {
        this.banCheckInterceptor = banCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(banCheckInterceptor)
                .excludePathPatterns("/user/login", "/login", "/banned");
    }
}
