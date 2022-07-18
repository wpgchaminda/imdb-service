package com.imdb.service.web.api.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BothActorsPlayedTogetherResponse extends PagingResponseBase {
  private List<BothActorsPlayedTogetherResult> data;
}
