package com.imdb.service.repository;

import com.imdb.service.domain.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre,Integer> {

  /**
   * Find By Name
   *
   * @param name
   * @return Genre
   */
  Genre findGenreByNameIgnoreCase(String name);

  /**
   * Is Exists By Name
   *
   * @param name
   * @return boolean
   */
  boolean existsByNameIgnoreCase(String name);
}
