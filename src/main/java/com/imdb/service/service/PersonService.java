package com.imdb.service.service;

import com.imdb.service.domain.Person;
import com.imdb.service.repository.PersonRepository;
import java.util.Optional;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  /**
   * Save
   *
   * @param person
   * @return Person
   */
  public Person save(Person person) {
    try {
      return personRepository.save(person);
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Find By Id
   *
   * @param id
   * @return Person
   */
  public Person findById(final String id) {
    try {
      Optional<Person> optional = personRepository.findById(id);
      return optional.isPresent() ? optional.get() : null;
    } catch (Exception e) {
      log.log(Level.SEVERE, e.getMessage(), e);
      throw e;
    }
  }
}
