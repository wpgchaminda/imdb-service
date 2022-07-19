package com.imdb.service.util;

import com.imdb.service.web.exception.BadRequestException;
import com.imdb.service.web.exception.ResourceNotFoundException;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log
@Aspect
@Component
public class RequestCountAspect {

  @Autowired
  private RequestCountUtil requestCountUtil;

  @Around("@annotation(requestCount)")
  public Object preventDuplicateProcess(ProceedingJoinPoint joinPoint,
                                        RequestCount requestCount) throws Throwable {
    requestCountUtil.updateRequestCount();

    //Execute the Method
    Object proceed = null;
    try {
      proceed = joinPoint.proceed();
    } catch (BadRequestException e) {
      throw e;
    } catch (ResourceNotFoundException e) {
      throw e;
    } catch(Exception e){
      throw new RuntimeException("System error");
    }

    return proceed;
  }

}
