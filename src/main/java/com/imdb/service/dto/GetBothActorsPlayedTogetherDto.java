package com.imdb.service.dto;

import com.imdb.service.domain.Person;
import com.imdb.service.domain.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetBothActorsPlayedTogetherDto {
 private Title title;
 private Person actor1;
 private Person actor2;
}
