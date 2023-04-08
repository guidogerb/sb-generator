package com.bluepantsmedia.exchange.user;

import com.bluepantsmedia.record.UserRecord;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange(url = "http://localhost:8080/user")
public interface UserClient {

    @GetExchange
    Flux<UserRecord> getAllUsers();
    
    @GetExchange("/{name}")
    Mono<UserRecord> getUserByLastName(@PathVariable("lastName") String lastName);
    
    @PostExchange
    Mono<UserRecord> addUser(@RequestBody UserRecord userRecord);
  
}
