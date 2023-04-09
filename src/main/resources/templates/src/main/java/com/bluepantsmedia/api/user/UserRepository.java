package com.bluepantsmedia.api.user;

import com.bluepantsmedia.record.UserRecord;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

@Observed(name="users")
public interface UserRepository extends ReactiveCrudRepository<UserRecord, Integer> {
  Mono<UserRecord> findById(Integer id);
}
