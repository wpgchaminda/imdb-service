package com.imdb.service.util;

import java.util.logging.Level;
import javax.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Log
@Component
public class RequestCountUtil {

  @Autowired
  private CacheManager cacheManager;

  @Value("${imdb.api.cache.name}")
  private String cacheName;
  @Value("${imdb.api.cache.requestcount.key}")
  private String cacheRequestCountKey;

  @PostConstruct
  public void init() {
    //Init the request count cache
    cacheManager.getCache(cacheName).put(cacheRequestCountKey,0);
  }

  public void updateRequestCount() {
    try {
      Integer count = 0;
      Integer requestCount = (Integer) cacheManager.getCache(cacheName).get(cacheRequestCountKey).get();
      if (requestCount != null) {
        count = requestCount;
      }
      count++;
      cacheManager.getCache(cacheName).put(cacheRequestCountKey, count);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  public int getRequestCount() {
    int retuenVal = 0;
    try {
      retuenVal = (Integer) cacheManager.getCache(cacheName).get(cacheRequestCountKey).get();
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
    }
    return retuenVal;
  }
}
