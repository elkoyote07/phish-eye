package com.jesus.phisheye.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class BasicConfig {

    @Value("${phisheye.whois.apikey}")
    private String whoisApiKey;
}
