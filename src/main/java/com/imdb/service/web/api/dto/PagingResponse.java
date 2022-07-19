package com.imdb.service.web.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingResponse<T> {
  private Integer count = 0;
  private Long totalCount = 0L;
  private Integer totalPages = 0;
  private Integer page = 0;
  private Integer pageSize = 0;
  private List<T> data;
}
