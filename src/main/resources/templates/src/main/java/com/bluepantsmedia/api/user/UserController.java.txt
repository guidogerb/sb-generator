package com.bluepantsmedia.api.user;

import com.bluepantsmedia.record.UserRecord;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserRepository repo;
  private ObservationRegistry observationRegistry;
  
  public UserController(UserRepository repo, ObservationRegistry observationRegistry) {
    this.repo = repo;
    this.observationRegistry = observationRegistry;
  }

  @GetMapping
  public Flux<UserRecord> allUsers() {
    return Observation.createNotStarted("allUsers", observationRegistry)
            .observe(this::allUsersObserved);
  }

  Flux<UserRecord> allUsersObserved() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  @Observed(name="userById")
  public Mono<UserRecord> userById(@PathVariable("id") Integer id) {
    return repo.findById(id).switchIfEmpty(Mono.error(new UserNotFoundException(id)));
  }
  
  @PostMapping
  public Mono<UserRecord> saveUser(@RequestBody UserRecord userRecord) {
    return repo.save(userRecord).switchIfEmpty(Mono.error(new UserAlreadyExistsException(userRecord)));
  }

}
