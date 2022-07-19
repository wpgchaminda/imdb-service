package com.imdb.service.repository;

import com.imdb.service.domain.Title;
import com.imdb.service.dto.GetBestSellingTitlesResult;
import com.imdb.service.dto.GetBothActorsPlayedTogetherResult;
import com.imdb.service.dto.GetDirectorAndWriterSamePersonResult;
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
  @Query("SELECT new com.imdb.service.dto.GetDirectorAndWriterSamePersonResult(t,p) " +
      "FROM Title t, Person p " +
      "JOIN t.titleCrews tc1 ON tc1.crewType.id = :directorId " +
      "JOIN t.titleCrews tc2 ON tc2.crewType.id = :writerId " +
      "WHERE " +
      "tc1.person.nconst = tc2.person.nconst " +
      "AND p.nconst = tc1.person.nconst ")
  Page<GetDirectorAndWriterSamePersonResult> getDirectorAndWriterSamePerson(@Param("directorId") Integer directorId,
                                                                            @Param("writerId") Integer writerId,
                                                                            Pageable pageable);

  /**
   * Get BothActorsPlayedTogether
   *
   * @param actor1
   * @param actor2
   * @param categoryId
   * @param pageable
   * @return
   */
  @Query("SELECT new com.imdb.service.dto.GetBothActorsPlayedTogetherResult(t,tp1.person,tp2.person) " +
      "FROM Title t " +
      "JOIN t.titlePrincipals tp1 ON tp1.category.id = :categoryId AND lower(tp1.person.primaryName)=lower(:actor1) " +
      "JOIN t.titlePrincipals tp2 ON tp2.category.id = :categoryId AND lower(tp2.person.primaryName)=lower(:actor2) ")
  Page<GetBothActorsPlayedTogetherResult> getBothActorsPlayedTogether(@Param("actor1") String actor1,
                                                                      @Param("actor2") String actor2,
                                                                      @Param("categoryId") Integer categoryId,
                                                                      Pageable pageable);

  @Query("SELECT new com.imdb.service.dto.GetBestSellingTitlesResult(t.startYear, t.id, " +
      "t.primaryTitle, t.averageRating, t.numVotes, MAX(t.averageRating * t.numVotes)) " +
      "FROM Title t " +
      "JOIN t.genres g ON lower(g.name)=lower(:genre) " +
      "WHERE " +
      "t.averageRating IS NOT null " +
      "AND t.numVotes IS NOT null " +
      "AND t.startYear IS NOT null "+
      "GROUP BY t.startYear " +
      "ORDER BY t.startYear ")
  Page<GetBestSellingTitlesResult> getBestSellingTitles(@Param("genre") String genre,
                                                        Pageable pageable);
}
