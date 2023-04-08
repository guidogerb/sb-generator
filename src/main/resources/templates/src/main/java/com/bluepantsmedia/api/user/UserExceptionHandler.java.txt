package com.bluepantsmedia.api.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ProblemDetail handlerUNFE(UserNotFoundException e) {
    ProblemDetail problemDetail = 
        ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND, e.getMessage());
    problemDetail.setProperty("extra info", "I don't know that user.");
    return problemDetail;
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ProblemDetail handlerUAEE(UserAlreadyExistsException e) {
    ProblemDetail problemDetail =
            ProblemDetail.forStatusAndDetail(
                    HttpStatus.CONFLICT, e.getMessage());
    problemDetail.setProperty("extra info", "Users cannot share distinct emails or uids.");
    return problemDetail;
  }
  
}
