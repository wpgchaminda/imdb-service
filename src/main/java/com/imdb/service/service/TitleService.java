package com.imdb.service.service;

import com.imdb.service.domain.Category;
import com.imdb.service.domain.Title;
import com.imdb.service.dto.GetBothActorsPlayedTogetherResult;
import com.imdb.service.dto.GetDirectorAndWriterSamePersonResult;
import com.imdb.service.enums.CrewTypeEnum;
import com.imdb.service.repository.CategoryRepository;
import com.imdb.service.repository.TitleRepository;
import java.util.Optional;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log
@Service
public class TitleService {

  @Autowired
  private TitleRepository titleRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  private final String CATEGORY_ACTOR="ACTOR";

  /**
   * Save
   *
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
   *
   * @param id
   * @return Title
   */
  public Title findById(final String id) {
    try {
      Optional<Title> optional = titleRepository.findById(id);
      return optional.isPresent() ? optional.get() : null;
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Get Titles which are directed & written by the same person
   *
   * @param pageable
   * @return Page<TitlePersonResult>
   */
  public Page<GetDirectorAndWriterSamePersonResult> getDirectorAndWriterSamePerson(final Pageable pageable) {
    try {
      return titleRepository.getDirectorAndWriterSamePerson(CrewTypeEnum.DIRECTOR.getId(),
          CrewTypeEnum.WRITER.getId(), pageable);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Get both actors played together
   *
   * @param actor1
   * @param actor2
   * @param pageable
   * @return Page<GetBothActorsPlayedTogetherResult>
   */
  public Page<GetBothActorsPlayedTogetherResult> getBothActorsPlayedTogether(String actor1,
                                                                             String actor2,
                                                                             Pageable pageable) {
    try {
      Integer categoryId = -1;
      Category category = categoryRepository.findCategoryByNameIgnoreCase(CATEGORY_ACTOR);
      if (category != null) {
        categoryId = category.getId();
      }

      return titleRepository.getBothActorsPlayedTogether(actor1, actor2, categoryId, pageable);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Get best selling titles per year
   *
   * @param genre
   * @param pageable
   * @return Page<Title>
   */
  public Page<Title> getBestSellingTitles(String genre, Pageable pageable) {
    try {
      return titleRepository.getBestSellingTitles(genre, pageable);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }
}
