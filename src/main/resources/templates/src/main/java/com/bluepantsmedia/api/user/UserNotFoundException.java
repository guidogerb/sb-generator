package com.bluepantsmedia.api.user;

public class UserNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1324673829984L;

  public UserNotFoundException(Integer id) {
    super("User with id " + id + " not found.");
  }
  
}
