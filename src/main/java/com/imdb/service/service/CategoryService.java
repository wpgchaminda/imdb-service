package com.imdb.service.service;

import com.imdb.service.domain.Category;
import com.imdb.service.repository.CategoryRepository;
import java.util.Optional;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  /**
   * Save
   * @param category
   * @return Category
   */
  public Category save(Category category) {
    try {
      return categoryRepository.save(category);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Id
   * @param id
   * @return Category
   */
  public Category findById(Integer id) {
    try {
      Optional<Category> optional = categoryRepository.findById(id);
      return optional.isPresent() ? optional.get() : null;
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Name
   * @param name
   * @return Category
   */
  public Category findByName(String name) {
    try {
      return categoryRepository.findCategoryByNameIgnoreCase(name);
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
      return categoryRepository.existsByNameIgnoreCase(name);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }
}
