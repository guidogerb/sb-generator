package com.bluepantsmedia.api;

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
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	ApplicationRunner dataLoader(UserRepository repo, UserClient client) {
		return args -> {
			repo.save(new UserRecord(
							null,
							"Gerber",
							"Gary",
							"guidogerb1966@outlook.com",null))
					.subscribe();
			repo.save(new UserRecord(
							null,
							"Larson",
							"Lambda",
							"lambda@outlook.com",null))
					.subscribe();
			client.getAllUsers()
					.subscribe(users -> {
						System.out.println("####### \n" + users.toString() + "\n\n");
					});

		};
	}

	@Bean
	HttpServiceProxyFactory httpServiceProxyFactory() {
		WebClient webClient = WebClient.create();
		WebClientAdapter webClientAdapter = WebClientAdapter.forClient(webClient);
		return HttpServiceProxyFactory.builder(webClientAdapter).build();
	}

	@Bean
	UserClient userClient(HttpServiceProxyFactory httpServiceProxyFactory) throws Exception {
		return httpServiceProxyFactory.createClient(UserClient.class);
	}
}
