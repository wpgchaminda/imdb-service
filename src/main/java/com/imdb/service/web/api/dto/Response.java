package com.imdb.service.web.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
  private T data;
}
