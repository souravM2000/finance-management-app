package com.kshirsa;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class GeneralConfig {

    private final Environment env;

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Scheduled(fixedDelay = 1000*60*5)
    void renderKeepAlive() {
        if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForEntity("https://kshirsa-money-backend-dev.onrender.com/kshirsa/api/v1/auth/index", String.class);
        }
    }
}
