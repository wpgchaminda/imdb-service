package com.imdb.service.web.api;

import static com.imdb.service.web.api.ApiCons.API_PATH;
import com.imdb.service.domain.Person;
import com.imdb.service.domain.Title;
import com.imdb.service.dto.GetBothActorsPlayedTogetherDto;
import com.imdb.service.dto.GetDirectorAndWriterSamePersonDto;
import com.imdb.service.service.PersonService;
import com.imdb.service.service.TitleService;
import com.imdb.service.util.RequestCount;
import com.imdb.service.util.RequestCountUtil;
import com.imdb.service.web.api.dto.BestSellingTitleResult;
import com.imdb.service.web.api.dto.BothActorsPlayedTogetherResult;
import com.imdb.service.web.api.dto.DirectorAndWriterSamePersonResult;
import com.imdb.service.web.api.dto.PagingResponse;
import com.imdb.service.web.api.dto.PersonResult;
import com.imdb.service.web.api.dto.RequestCountResult;
import com.imdb.service.web.api.dto.Response;
import com.imdb.service.web.api.dto.TitleCrewResult;
import com.imdb.service.web.api.dto.TitlePrincipalResult;
import com.imdb.service.web.api.dto.TitleResult;
import com.imdb.service.web.exception.BadRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Log
@RestController
@RequestMapping(API_PATH +"/"+ImdbRestService.IMDB_API_PATH+"/"+ ImdbRestService.IMDB_API_VERSION)
@Api(value = "", tags = {"IMDB-Service"})
@Tag(name = "IMDB-Service",
     description = "Provide RESTful APIs to retrieve data from the IMDB Datasets")
public class ImdbRestService {

  public static final String IMDB_API_PATH = "imdbapi";
  public static final String IMDB_API_VERSION = "v1";

  @Autowired
  private TitleService titleService;
  @Autowired
  private PersonService personService;
  @Autowired
  private RequestCountUtil requestCountUtil;


  @Value("${imdb.api.paginations.max.page.size}")
  private int maxPageSize;
  @Value("${imdb.api.max.name.length}")
  private int maxNameLength;
  @Value("${imdb.api.max.id.length}")
  private int maxIdLength;
  @Value("${imdb.api.max.genre.length}")
  private int maxGenreLength;

  /**
   * Echo Service to check the the API is up and running
   *
   * @return String
   */
  @ApiOperation("Echo API to check the service is up and running")
  @RequestMapping(path = "/echo",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @RequestCount
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
  @ApiOperation("Get Title by ID")
  @RequestMapping(path = "/titles/{id}",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @RequestCount
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  Response<TitleResult> getTitleById(@PathVariable String id) {

    //Validation
    getTitleByIdValidation(id);

    //Query results
    Title title = titleService.findById(id.trim());

    //Construct response
    Response<TitleResult> response = getTitleByIdResponse(title);
    return response;
  }

  /**
   * Get Title by id
   *
   * @param id
   * @return GetTitleByIdResponse
   */
  @ApiOperation("Get Person by ID")
  @RequestMapping(path = "/persons/{id}",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @RequestCount
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  Response<PersonResult> getPersonById(@PathVariable String id) {

    //Validation
    getPersonByIdValidation(id);

    //Query results
    Person person = personService.findById(id.trim());

    //Construct response
    Response<PersonResult> response = getPersonByIdResponse(person);
    return response;
  }

  /**
   * Get Titles which are directed & written by the same person
   *
   * @param page
   * @param pageSize
   * @return DirectorAndWriterSamePersonResponse
   */
  @ApiOperation("Get Titles which are directed & written by the same person")
  @RequestMapping(path = "/titles/director-and-writer-same-person",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @RequestCount
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  PagingResponse<DirectorAndWriterSamePersonResult> getTitlesDirectorAndWriterSamePerson(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "pagesize", defaultValue = "10") int pageSize) {

    //Validation
    getTitlesDirectorAndWriterSamePersonValidate(page, pageSize);

    //Pageable
    Pageable pageable = PageRequest.of(page, pageSize);

    //Get Query Results
    Page<GetDirectorAndWriterSamePersonDto> queryResults = titleService.getDirectorAndWriterSamePerson(pageable);

    //Construct Response
    PagingResponse<DirectorAndWriterSamePersonResult> response =
        getTitlesDirectorAndWriterSamePersonResponse(queryResults);
    return response;
  }

  /**
   * Get Titles which are both actors played together
   *
   * @param page
   * @param pageSize
   * @return DirectorAndWriterSamePersonResponse
   */
  @ApiOperation("Get Titles which are both actors played together")
  @RequestMapping(path = "/titles/both-actors-played-together",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @RequestCount
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  PagingResponse<BothActorsPlayedTogetherResult> getTitlesBothActorsPlayedTogether(
      @RequestParam(name = "actor1", required = true) String actor1,
      @RequestParam(name = "actor2", required = true) String actor2,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "pagesize", defaultValue = "10") int pageSize) {

    //Validation
    getTitlesBothActorsPlayedTogetherValidation(actor1, actor2, page, pageSize);

    //Pageable
    Pageable pageable = PageRequest.of(page, pageSize);

    //Get Query Results
    Page<GetBothActorsPlayedTogetherDto> queryResults =
        titleService.getBothActorsPlayedTogether(actor1.trim(), actor2.trim(), pageable);

    //Construct Response
    PagingResponse<BothActorsPlayedTogetherResult> response =
        getTitlesBothActorsPlayedTogetherResponse(queryResults);
    return response;
  }

  /**
   * Get best selling Titles in each year
   *
   * @param genre
   * @return GetTitleByIdResponse
   */
  @ApiOperation("Get best selling Titles in each year")
  @RequestMapping(path = "/titles/best-selling-titles",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @RequestCount
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  PagingResponse<BestSellingTitleResult> getBestSellingTitles(
      @RequestParam(name = "genre", required = true) String genre,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "pagesize", defaultValue = "10") int pageSize) {

    //Validation
    getBestSellingTitlesValidation(genre, page, pageSize);

    Pageable pageable = PageRequest.of(page, pageSize);

    //Query results
    Page<Title> bestSellingTitles = titleService.getBestSellingTitles(genre,
        pageable);

    //Response
    PagingResponse<BestSellingTitleResult> response =
        getBestSellingTitlesValidationResponse(bestSellingTitles);
    return response;
  }

  /**
   * Get Request Count
   *
   * @return String
   */
  @ApiOperation("Get Request Count")
  @RequestMapping(path = "/request-count",
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @RequestCount
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody
  Response<RequestCountResult> getRequestCount() {
    Response<RequestCountResult> response=new Response<>();
    RequestCountResult requestCountResult=new RequestCountResult(requestCountUtil.getRequestCount());
    response.setData(requestCountResult);
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

    if (id.trim().length() > maxIdLength) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"id"});
    }
  }

  /**
   * Create GetTitleByIdResponse
   *
   * @param title
   * @return Response<TitleResult>
   */
  private Response<TitleResult> getTitleByIdResponse(final Title title) {
    Response<TitleResult> response = new Response<>();
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

    if (id.trim().length() > maxIdLength) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"id"});
    }
  }

  /**
   * Create GetPersonByIdResponse
   *
   * @param person
   * @return Response<PersonResult>
   */
  private Response<PersonResult> getPersonByIdResponse(final Person person) {
    Response<PersonResult> response = new Response<>();
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

  /**
   * Validate getDirectorAndWriterSamePerson
   *
   * @param page
   * @param pageSize
   */
  private void getTitlesDirectorAndWriterSamePersonValidate(final int page, final int pageSize) {
    if (page < 0) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"page"});
    }

    if (pageSize < 1 || pageSize > maxPageSize) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"pageSize"});
    }
  }

  /**
   * Create GetDirectorAndWriterSamePersonResponse
   *
   * @param queryResults
   * @return PagingResponse<DirectorAndWriterSamePersonResult>
   */
  private PagingResponse<DirectorAndWriterSamePersonResult>
  getTitlesDirectorAndWriterSamePersonResponse(final Page<GetDirectorAndWriterSamePersonDto> queryResults) {
    PagingResponse<DirectorAndWriterSamePersonResult> response = new PagingResponse<>();

    if (queryResults.hasContent()) {
      response.setPage(queryResults.getNumber());
      response.setPageSize(queryResults.getSize());
      response.setTotalPages(queryResults.getTotalPages());
      response.setCount(queryResults.getNumberOfElements());
      response.setTotalCount(queryResults.getTotalElements());

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
   * Validation getTitlesBothActorsPlayedTogether
   *
   * @param actor1
   * @param actor2
   * @param page
   * @param pageSize
   */
  private void getTitlesBothActorsPlayedTogetherValidation(final String actor1,
                                                           final String actor2,
                                                           final int page,
                                                           final int pageSize) {
    if (!StringUtils.hasText(actor1)) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"actor1"});
    }

    if (actor1.trim().length() > maxNameLength) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"actor1"});
    }

    if (!StringUtils.hasText(actor2)) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"actor2"});
    }

    if (actor2.trim().length() > maxNameLength) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"actor2"});
    }

    if (page < 0) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"page"});
    }

    if (pageSize < 1 || pageSize > maxPageSize) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"pageSize"});
    }
  }

  /**
   * Construct getTitlesBothActorsPlayedTogether response
   *
   * @param queryResults
   * @return PagingResponse<BothActorsPlayedTogetherResult>
   */
  private PagingResponse<BothActorsPlayedTogetherResult>
  getTitlesBothActorsPlayedTogetherResponse(final Page<GetBothActorsPlayedTogetherDto> queryResults) {
    PagingResponse<BothActorsPlayedTogetherResult> response = new PagingResponse<>();

    if (queryResults.hasContent()) {
      response.setPage(queryResults.getNumber());
      response.setPageSize(queryResults.getSize());
      response.setTotalPages(queryResults.getTotalPages());
      response.setCount(queryResults.getNumberOfElements());
      response.setTotalCount(queryResults.getTotalElements());

      List<BothActorsPlayedTogetherResult> resultData = queryResults.map(x -> new BothActorsPlayedTogetherResult(
          x.getTitle().getId(),
          x.getTitle().getPrimaryTitle(),
          x.getActor1().getNconst(),
          x.getActor1().getPrimaryName(),
          x.getActor2().getNconst(),
          x.getActor2().getPrimaryName()))
          .toList();
      response.setData(resultData);
    }
    return response;
  }

  /**
   * Validate getBestSellingTitles
   *
   * @param genre
   * @param page
   * @param pageSize
   */
  private void getBestSellingTitlesValidation(final String genre, int page, int pageSize) {
    if (!StringUtils.hasText(genre)) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"genre"});
    }

    if (genre.trim().length() > maxGenreLength) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"genre"});
    }

    if (page < 0) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"page"});
    }

    if (pageSize < 1 || pageSize > maxPageSize) {
      throw new BadRequestException("imdb.service.error.input.parameter.invalid", new String[] {"pageSize"});
    }
  }

  /**
   * Create getBestSellingTitlesValidationResponse
   *
   * @param queryResults
   * @return
   */
  private PagingResponse<BestSellingTitleResult>
  getBestSellingTitlesValidationResponse(final Page<Title> queryResults) {
    PagingResponse<BestSellingTitleResult> response = new PagingResponse<>();

    if (queryResults.hasContent()) {
      response.setPage(queryResults.getNumber());
      response.setPageSize(queryResults.getSize());
      response.setTotalPages(queryResults.getTotalPages());
      response.setCount(queryResults.getNumberOfElements());
      response.setTotalCount(queryResults.getTotalElements());

      List<BestSellingTitleResult> resultData = queryResults.map(x -> new BestSellingTitleResult(
          x.getStartYear(),
          x.getId(),
          x.getPrimaryTitle(),
          x.getAverageRating(),
          x.getNumVotes()
      )).toList();
      response.setData(resultData);
    }
    return response;
  }
}
