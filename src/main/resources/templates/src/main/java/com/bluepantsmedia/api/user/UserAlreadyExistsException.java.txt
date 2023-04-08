package com.bluepantsmedia.api.user;

import com.bluepantsmedia.record.UserRecord;
import org.springframework.dao.DataIntegrityViolationException;

public class UserAlreadyExistsException extends DataIntegrityViolationException {

  private static final long serialVersionUID = 1324673829984L;

  public UserAlreadyExistsException(UserRecord userRecord) {
    super("User with email " + userRecord.email() + " already exists.");
  }
  
}
