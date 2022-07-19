package com.imdb.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

  @Value("${imdb.api.cache.name}")
  private String cacheName;

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager(cacheName);
  }
}
