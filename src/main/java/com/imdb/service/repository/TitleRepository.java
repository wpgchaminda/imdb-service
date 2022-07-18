package com.imdb.service.repository;

import com.imdb.service.domain.Title;
import com.imdb.service.dto.TitlePersonResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends PagingAndSortingRepository<Title,String> {

  /**
   * Get Titles which are directed & written by the same person
   *
   * @param directorId
   * @param writerId
   * @param pageable
   * @return Page<TitlePersonResult>
   */
  @Query("SELECT new com.imdb.service.dto.TitlePersonResult(t,p) " +
      "FROM Title t, Person p " +
      "JOIN t.titleCrews tc1 ON tc1.crewType.id = :directorId " +
      "JOIN t.titleCrews tc2 ON tc2.crewType.id = :writerId " +
      "WHERE " +
      "tc1.person.nconst = tc2.person.nconst " +
      "AND p.nconst = tc1.person.nconst ")
  Page<TitlePersonResult> getDirectorAndWriterSamePerson(@Param("directorId") Integer directorId,
                                                         @Param("writerId") Integer writerId,
                                                         Pageable pageable);
}
