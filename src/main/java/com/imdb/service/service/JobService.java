package com.imdb.service.service;

import com.imdb.service.domain.Job;
import com.imdb.service.repository.JobRepository;
import java.util.Optional;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class JobService {

  @Autowired
  private JobRepository jobRepository;

  /**
   * Save
   * @param job
   * @return Job
   */
  public Job save(Job job) {
    try {
      return jobRepository.save(job);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Id
   * @param id
   * @return Job
   */
  public Job findById(Integer id) {
    try {
      Optional<Job> optional = jobRepository.findById(id);
      return optional.isPresent() ? optional.get() : null;
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Name
   * @param name
   * @return Job
   */
  public Job findByName(String name) {
    try {
      return jobRepository.findJobByNameIgnoreCase(name);
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
      return jobRepository.existsByNameIgnoreCase(name);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }
}
