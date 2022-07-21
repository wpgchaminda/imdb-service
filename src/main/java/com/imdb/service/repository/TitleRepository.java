package com.imdb.service.repository;

import com.imdb.service.domain.Title;
import com.imdb.service.dto.GetBothActorsPlayedTogetherDto;
import com.imdb.service.dto.GetDirectorAndWriterSamePersonDto;
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
  @Query("SELECT new com.imdb.service.dto.GetDirectorAndWriterSamePersonDto(t,p) " +
      "FROM Title t, Person p " +
      "JOIN t.titleCrews tc1 ON tc1.crewType.id = :directorId " +
      "JOIN t.titleCrews tc2 ON tc2.crewType.id = :writerId " +
      "WHERE " +
      "tc1.person.nconst = tc2.person.nconst " +
      "AND p.nconst = tc1.person.nconst ")
  Page<GetDirectorAndWriterSamePersonDto> getDirectorAndWriterSamePerson(@Param("directorId") Integer directorId,
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
  @Query("SELECT new com.imdb.service.dto.GetBothActorsPlayedTogetherDto(t,tp1.person,tp2.person) " +
      "FROM Title t " +
      "JOIN t.titlePrincipals tp1 ON tp1.category.id = :categoryId AND UPPER(tp1.person.primaryName)=UPPER(:actor1) " +
      "JOIN t.titlePrincipals tp2 ON tp2.category.id = :categoryId AND UPPER(tp2.person.primaryName)=UPPER(:actor2) ")
  Page<GetBothActorsPlayedTogetherDto> getBothActorsPlayedTogether(@Param("actor1") String actor1,
                                                                   @Param("actor2") String actor2,
                                                                   @Param("categoryId") Integer categoryId,
                                                                   Pageable pageable);

  /**
   * Get best selling titles by year
   *
   * @param genre
   * @param pageable
   * @return
   */
  @Query(value = "SELECT t.* FROM title t " +
      "INNER JOIN title_genre tg ON tg.title_id=t.id " +
      "INNER JOIN genre g ON g.id=tg.genre_id AND UPPER(g.name)=UPPER(:genre) " +
      "INNER JOIN (SELECT tt.start_year,MAX(tt.average_rating*tt.num_votes) AS rank " +
      "            FROM title tt " +
      "            INNER JOIN title_genre tgg ON tgg.title_id=tt.id " +
      "            INNER JOIN genre gg ON gg.id=tgg.genre_id AND UPPER(gg.name)=UPPER(:genre) " +
      "            WHERE " +
      "            tt.start_year IS NOT NULL " +
      "            AND tt.average_rating IS NOT NULL " +
      "            AND tt.num_votes IS NOT NULL " +
      "            GROUP BY tt.start_year) ta " +
      "            ON t.start_year=ta.start_year AND (t.average_rating*t.num_votes)=ta.rank " +
      "WHERE " +
      "t.start_year IS NOT NULL " +
      "AND t.average_rating IS NOT NULL " +
      "AND t.num_votes IS NOT NULL " +
      "ORDER BY t.start_year",
      countQuery = "SELECT COUNT(t.id) FROM title t " +
          "INNER JOIN title_genre tg ON tg.title_id=t.id " +
          "INNER JOIN genre g ON g.id=tg.genre_id AND UPPER(g.name)=UPPER(:genre) " +
          "INNER JOIN (SELECT tt.start_year,MAX(tt.average_rating*tt.num_votes) AS rating " +
          "            FROM title tt " +
          "            INNER JOIN title_genre tgg ON tgg.title_id=tt.id " +
          "            INNER JOIN genre gg ON gg.id=tgg.genre_id AND UPPER(gg.name)=UPPER(:genre) " +
          "            WHERE " +
          "            tt.start_year IS NOT NULL " +
          "            AND tt.average_rating IS NOT NULL " +
          "            AND tt.num_votes IS NOT NULL " +
          "            GROUP BY tt.start_year) ta " +
          "            ON t.start_year=ta.start_year AND (t.average_rating*t.num_votes)=ta.rating " +
          "WHERE " +
          "t.start_year IS NOT NULL " +
          "AND t.average_rating IS NOT NULL " +
          "AND t.num_votes IS NOT NULL ",
      nativeQuery = true)
  Page<Title> getBestSellingTitles(@Param("genre") String genre,
                                   Pageable pageable);
}
