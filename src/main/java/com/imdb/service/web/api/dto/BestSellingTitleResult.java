package com.imdb.service.web.api.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BestSellingTitleResult {
  private Integer year;
  private String titleId;
  private String primaryTitle;
  private BigDecimal rating;
  private Integer votes;
}
