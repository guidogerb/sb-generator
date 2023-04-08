package com.bluepantsmedia;

import com.bluepantsmedia.utils.FilesUtil;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class GeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneratorApplication.class, args);
	}

	@Bean
	ApplicationRunner thymeleafTemplateProcessor(TemplateEngine templateEngine) {
		return args -> {
			System.out.println("Generator Application started.");

			System.out.println("Deleting previously generated Thymeleaf templates.");
			//Path projectRoot = Paths.get(System.getProperty("user.dir"));
			//Path genDir = projectRoot.resolve("generated");
			Path genDir = Paths.get("generated");
			FilesUtil.deleteDirectory(genDir);
			Files.createDirectories(genDir);

			// Configure the Thymeleaf template resolver and engine
			ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
			templateResolver.setPrefix("/");
			templateResolver.setSuffix(".txt");
			templateResolver.setTemplateMode("TEXT");
			templateResolver.setCharacterEncoding("UTF-8");

			templateEngine.setTemplateResolver(templateResolver);

			// Create a context with variables to be used in the template
			//Context context = new Context();
			//context.setVariable("name", "John Doe");

			// Render the template with the context
			String javaCode = null; // templateEngine.process("HelloWorldTemplate", context);

			// Output the generated Java source code
			System.out.println(javaCode);
		};
	}
}
