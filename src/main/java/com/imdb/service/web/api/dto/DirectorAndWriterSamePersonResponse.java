package com.imdb.service.web.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectorAndWriterSamePersonResponse extends PagingResponseBase {
  private List<DirectorAndWriterSamePersonResult> data;
}
