package com.imdb.service.service;

import com.imdb.service.domain.Title;
import com.imdb.service.repository.TitleRepository;
import java.util.Optional;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class TitleService {

  @Autowired
  private TitleRepository titleRepository;

  /**
   * Save
   * @param title
   * @return Title
   */
  public Title save(Title title) {
    try {
      return titleRepository.save(title);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Id
   * @param id
   * @return Title
   */
  public Title findById(String id) {
    try {
      Optional<Title> optional = titleRepository.findById(id);
      return optional.isPresent() ? optional.get() : null;
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }
}
