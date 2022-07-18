package com.imdb.service.service;

import com.imdb.service.domain.TitleType;
import com.imdb.service.repository.TitleTypeRepository;
import java.util.Optional;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class TitleTypeService {

  @Autowired
  private TitleTypeRepository titleTypeRepository;

  /**
   * Save
   * @param titleType
   * @return TitleType
   */
  public TitleType save(TitleType titleType) {
    try {
      return titleTypeRepository.save(titleType);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Id
   * @param id
   * @return TitleType
   */
  public TitleType findById(Integer id) {
    try {
      Optional<TitleType> optional = titleTypeRepository.findById(id);
      return optional.isPresent() ? optional.get() : null;
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Name
   * @param name
   * @return TitleType
   */
  public TitleType findByName(String name) {
    try {
      return titleTypeRepository.findTitleTypeByNameIgnoreCase(name);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Is Exists By Name
   * @param name
   * @return boolean
   */
  public boolean existByName(String name) {
    try {
      return titleTypeRepository.existsByNameIgnoreCase(name);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }
}
