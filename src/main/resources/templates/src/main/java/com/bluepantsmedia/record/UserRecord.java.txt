package com.bluepantsmedia.record;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record UserRecord(@Id Integer id, String lastName, String firstName, String email, LocalDateTime dateCreated) {
}
