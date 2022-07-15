package com.imdb.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImdbServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ImdbServiceApplication.class, args);

    //Load Data
    loadData();
  }

  /**
   * Loading IMDB datasets into the in-memory H2 database
   */
  private static void loadData(){

  }

}
