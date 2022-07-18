package com.imdb.service.web.api;

import static com.imdb.service.web.api.ApiCons.API_PATH;


import com.imdb.service.domain.Person;
import com.imdb.service.domain.Title;
import com.imdb.service.dto.TitlePersonResult;
import com.imdb.service.service.PersonService;
import com.imdb.service.service.TitleService;
import com.imdb.service.web.api.dto.DirectorAndWriterSamePersonResponse;
import com.imdb.service.web.api.dto.DirectorAndWriterSamePersonResult;
import com.imdb.service.web.api.dto.GetPersonByIdResponse;
import com.imdb.service.web.api.dto.GetTitleByIdResponse;
import com.imdb.service.web.api.dto.PagingResponseBase;
import com.imdb.service.web.api.dto.PersonResult;
import com.imdb.service.web.api.dto.ResponseBase;
import com.imdb.service.web.api.dto.TitleCrewResult;
import com.imdb.service.web.api.dto.TitlePrincipalResult;
import com.imdb.service.web.api.dto.TitleResult;
import com.imdb.service.web.exception.BadRequestException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_PATH +"/"+ImdbRestService.IMDB_API_PATH+"/"+ ImdbRestService.IMDB_API_VERSION)
public class ImdbRestService {

  public static final String IMDB_API_PATH = "imdbapi";
  public static final String IMDB_API_VERSION = "1";

  @Autowired
  private TitleService titleService;
  @Autowired
  private PersonService personService;

  /**
   * Echo Service to check the the API is up and running
   *
   * @return String
   */
  @RequestMapping(path = "/echo",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  String echo() {
    return "Echo";
  }

  /**
   * Get Title by id
   *
   * @param id
   * @return GetTitleByIdResponse
   */
  @RequestMapping(path = "/titles/{id}",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  ResponseBase getTitleById(@PathVariable String id) {
    //Validation
    getTitleByIdValidation(id);

    //Query results
    Title title = titleService.findById(id);

    //Construct response
    GetTitleByIdResponse response = getTitleByIdResponse(title);
    return response;
  }

  /**
   * Get Title by id
   *
   * @param id
   * @return GetTitleByIdResponse
   */
  @RequestMapping(path = "/persons/{id}",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  ResponseBase getPersonById(@PathVariable String id) {
    //Validation
    getPersonByIdValidation(id);

    //Query results
    Person person = personService.findById(id);

    //Construct response
    GetPersonByIdResponse response = getPersonByIdResponse(person);
    return response;
  }

  /**
   * Get Titles which are directed & written by the same person
   *
   * @param page
   * @param pageSize
   * @return DirectorAndWriterSamePersonResponse
   */
  @RequestMapping(path = "/titles/directorandwritersameperson",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  PagingResponseBase getDirectorAndWriterSamePerson(@RequestParam(name = "page", defaultValue = "0") int page,
                                                    @RequestParam(name = "pagesize", defaultValue = "10") int pageSize) {
    //Validation
    getDirectorAndWriterSamePersonValidate(page, pageSize);

    //Pageable
    Pageable pageable = PageRequest.of(page, pageSize);

    //Get Query Results
    Page<TitlePersonResult> queryResults = titleService.getDirectorAndWriterSamePerson(pageable);

    //Construct Response
    DirectorAndWriterSamePersonResponse response =
        getDirectorAndWriterSamePersonResponse(queryResults);
    return response;
  }

  /**
   * Validate getDirectorAndWriterSamePerson
   *
   * @param page
   * @param pageSize
   */
  private void getDirectorAndWriterSamePersonValidate(final int page, final int pageSize) {
    if (page < 0) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"page"});
    }

    if (pageSize < 1 || pageSize > 100) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"pageSize"});
    }
  }

  /**
   * Create GetDirectorAndWriterSamePersonResponse
   *
   * @param queryResults
   * @return DirectorAndWriterSamePersonResponse
   */
  private DirectorAndWriterSamePersonResponse
  getDirectorAndWriterSamePersonResponse(final Page<TitlePersonResult> queryResults) {
    DirectorAndWriterSamePersonResponse response = new DirectorAndWriterSamePersonResponse();

    if (queryResults.hasContent()) {
      response.setPage(queryResults.getNumber());
      response.setPageSize(queryResults.getSize());
      response.setTotal(queryResults.getTotalElements());
      response.setTotalPages(queryResults.getTotalPages());

      List<DirectorAndWriterSamePersonResult> resultData = queryResults.map(x -> new DirectorAndWriterSamePersonResult(
          x.getTitle().getId(),
          x.getTitle().getPrimaryTitle(),
          x.getPerson().getNconst(),
          x.getPerson().getPrimaryName(),
          x.getPerson().getDeathYear() == null))
          .toList();
      response.setData(resultData);
    }
    return response;
  }

  /**
   * Validate getTitleById
   *
   * @param id
   */
  private void getTitleByIdValidation(final String id) {
    if (!StringUtils.hasText(id)) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"id"});
    }

    if (id.trim().length() > 25) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"id"});
    }
  }

  /**
   * Create GetTitleByIdResponse
   *
   * @param title
   * @return GetTitleByIdResponse
   */
  private GetTitleByIdResponse getTitleByIdResponse(final Title title) {
    GetTitleByIdResponse response = new GetTitleByIdResponse();
    if (title != null) {
      //Title result
      TitleResult titleResult = new TitleResult();
      titleResult.setId(title.getId());
      titleResult.setTypeId(title.getTitleType() != null ? title.getTitleType().getId() : null);
      titleResult.setType(title.getTitleType() != null ? title.getTitleType().getName() : null);
      titleResult.setPrimaryTitle(title.getPrimaryTitle());
      titleResult.setOriginalTitle(title.getOriginalTitle());
      titleResult.setIsAdult(title.getIsAdult());
      titleResult.setStartYear(title.getStartYear());
      titleResult.setEndYear(title.getEndYear());
      titleResult.setRuntimeMinutes(title.getRuntimeMinutes());
      titleResult.setAverageRating(title.getAverageRating());
      titleResult.setNumVotes(title.getNumVotes());

      //Genres
      if (title.getGenres() != null && title.getGenres().size() > 0) {
        List<String> genres = title.getGenres().stream().map(x -> x.getName()).collect(Collectors.toList());
        titleResult.setGenres(genres);
      }

      //Crews
      if (title.getTitleCrews() != null && title.getTitleCrews().size() > 0) {
        List<TitleCrewResult> titleCrewResults =
            title.getTitleCrews().stream().map(x ->
                new TitleCrewResult(
                    x.getPerson() != null ? x.getPerson().getNconst() : null,
                    x.getPerson() != null ? x.getPerson().getPrimaryName() : null,
                    x.getCrewType() != null ? x.getCrewType().getId() : null,
                    x.getCrewType() != null ? x.getCrewType().getName() : null))
                .collect(Collectors.toList());
        titleResult.setCrews(titleCrewResults);
      }

      //Principals
      if (title.getTitlePrincipals() != null && title.getTitlePrincipals().size() > 0) {
        List<TitlePrincipalResult> titlePrincipalResults =
            title.getTitlePrincipals().stream().map(x ->
                new TitlePrincipalResult(
                    x.getPerson() != null ? x.getPerson().getNconst() : null,
                    x.getPerson() != null ? x.getPerson().getPrimaryName() : null,
                    x.getCategory() != null ? x.getCategory().getId() : null,
                    x.getCategory() != null ? x.getCategory().getName() : null,
                    x.getJob() != null ? x.getJob().getId() : null,
                    x.getJob() != null ? x.getJob().getName() : null,
                    x.getCharacters()))
                .collect(Collectors.toList());
        titleResult.setPrincipals(titlePrincipalResults);
      }
      response.setData(titleResult);
    }
    return response;
  }

  /**
   * Validate getPersonById
   *
   * @param id
   */
  private void getPersonByIdValidation(final String id) {
    if (!StringUtils.hasText(id)) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"id"});
    }

    if (id.trim().length() > 25) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"id"});
    }
  }

  /**
   * Create GetPersonByIdResponse
   *
   * @param person
   * @return GetPersonByIdResponse
   */
  private GetPersonByIdResponse getPersonByIdResponse(final Person person) {
    GetPersonByIdResponse response = new GetPersonByIdResponse();
    if (person != null) {
      PersonResult personResult = new PersonResult();
      personResult.setId(person.getNconst());
      personResult.setPrimaryName(person.getPrimaryName());
      personResult.setBirthYear(person.getBirthYear());
      personResult.setDeathYear(person.getDeathYear());
      personResult.setPrimaryProfession(person.getPrimaryProfession());
      personResult.setKnownForTitles(person.getKnownForTitles());
      response.setData(personResult);
    }
    return response;
  }
}
