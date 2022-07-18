package com.imdb.service.web.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("imdb-api")
public class ImdbRestService {

  /**
   * Echo Service to check the the API is up and running
   * @return String
   */
  @RequestMapping(path = "echo", method = RequestMethod.GET)
  public HttpEntity<String> echo() {
    return ResponseEntity.ok("Echo");
  }


}
