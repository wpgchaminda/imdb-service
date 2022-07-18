package com.imdb.service.repository;

import com.imdb.service.domain.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<Job,Integer> {

  /**
   * Find By Name
   * @param name
   * @return Job
   */
  Job findJobByNameIgnoreCase(String name);

  /**
   * Is Exists By Name
   * @param name
   * @return boolean
   */
  boolean existsByNameIgnoreCase(String name);
}
