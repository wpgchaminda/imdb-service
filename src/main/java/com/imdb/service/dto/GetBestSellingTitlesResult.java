package com.imdb.service.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetBestSellingTitlesResult {
  private Integer year;
  private String titleId;
  private String primaryTitle;
  private BigDecimal rating;
  private Integer votes;
  private BigDecimal rate;
}
