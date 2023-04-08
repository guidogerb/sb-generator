package com.bluepantsmedia;

import com.bluepantsmedia.exchange.user.UserClient;
import com.bluepantsmedia.record.UserRecord;
import com.bluepantsmedia.api.user.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class GeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	ApplicationRunner dataLoader(UserRepository repo, UserClient client) {
		return args -> {
			
		};
	}
}
