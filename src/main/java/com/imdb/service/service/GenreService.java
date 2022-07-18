package com.imdb.service.service;

import com.imdb.service.domain.Genre;
import com.imdb.service.repository.GenreRepository;
import java.util.Optional;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class GenreService {

  @Autowired
  private GenreRepository genreRepository;

  /**
   * Save
   *
   * @param genre
   * @return Genre
   */
  public Genre save(Genre genre) {
    try {
      return genreRepository.save(genre);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Id
   *
   * @param id
   * @return CrewType
   */
  public Genre findById(final Integer id) {
    try {
      Optional<Genre> optional = genreRepository.findById(id);
      return optional.isPresent() ? optional.get() : null;
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Name
   *
   * @param name
   * @return CrewType
   */
  public Genre findByName(final String name) {
    try {
      return genreRepository.findGenreByNameIgnoreCase(name);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Is Exists By Name
   *
   * @param name
   * @return boolean
   */
  public boolean existByName(final String name) {
    try {
      return genreRepository.existsByNameIgnoreCase(name);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }
}
