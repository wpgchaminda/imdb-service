package com.imdb.service.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TitlePrincipalResult {
  private String personId;
  private String personName;
  private Integer categoryId;
  private String category;
  private Integer jobId;
  private String job;
  private String characters;
}
