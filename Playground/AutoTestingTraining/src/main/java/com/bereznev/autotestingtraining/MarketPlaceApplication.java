package com.bereznev.autotestingtraining;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MarketPlaceApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MarketPlaceApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(MarketPlaceApplication.class);
    }
}
