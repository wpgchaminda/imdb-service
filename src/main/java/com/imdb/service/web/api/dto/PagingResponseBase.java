package com.imdb.service.web.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PagingResponseBase {
  private Long total=0L;
  private Integer totalPages=0;
  private Integer page=0;
  private Integer pageSize=0;
}
