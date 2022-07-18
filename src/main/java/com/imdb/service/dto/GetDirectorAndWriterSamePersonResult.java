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
public class GetDirectorAndWriterSamePersonResult {
 private Title title;
 private Person person;
}
