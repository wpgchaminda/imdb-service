package com.imdb.service.repository;

import com.imdb.service.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends CrudRepository<Title,String> {
}
