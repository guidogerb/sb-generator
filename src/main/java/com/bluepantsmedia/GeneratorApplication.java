package com.bluepantsmedia;

import com.bluepantsmedia.utils.FilesUtil;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class GeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneratorApplication.class, args);
	}

	@Bean
	ApplicationRunner thymeleafTemplateProcessor(TemplateEngine templateEngine, Environment env) {
		return args -> {
			System.out.println("Generator Application started.");

			System.out.println("Deleting previously generated Thymeleaf templates.");
			Path projectRoot = Paths.get(System.getProperty("user.dir"));
			Path genDir = projectRoot.resolve("generated");
			FilesUtil.deleteDirectory(genDir);
			Files.createDirectories(genDir);
			Path templatesDirectory = projectRoot.resolve("src").resolve("main").resolve("resources").resolve("templates");

			addTemplateResolvers(templateEngine);

			// Process and copy the Thymeleaf templates
			processAndCopyTemplates(templateEngine, templatesDirectory, genDir, env);

		};
	}

	private void processAndCopyTemplates(TemplateEngine templateEngine, Path templatesDirectory, Path genDir, Environment env) throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources(templatesDirectory.toUri()
				.toURL().toString() + "/**/*.*");

		for (Resource resource : resources) {
			// Get paths of the template file and destination file
			final Path templatePath = Path.of(Paths.get(resource.getURI()).toString());
			final String filename = templatePath.getFileName().toString();
			final String fileNameNoExtension = removeFileExtension(filename);
			final String fileExtension = getFileExtension(filename);
			final Path outFile = Path.of(Paths.get(resource.getURI()).toString().replace("src\\main\\resources\\templates", "generated"));

			// Create the output directory if it doesn't exist
			Files.createDirectories(outFile.getParent());

			// Process the template and write the result to FileWriter
			Context context = new Context();
			if(filename.equals("pom.xml")) {
				context.setVariable("decription", env.getProperty("pom.artifact.description"));
				context.setVariable("artifact-id", env.getProperty("pom.artifact.id"));
				context.setVariable("name", env.getProperty("pom.artifact.name"));
			}
			// TODO Add variables needed for your templates here

			try (FileWriter writer = new FileWriter(outFile.toFile())) {
				templateEngine.process(fileNameNoExtension, context, writer);
			}
		}
	}

	private static void addTemplateResolvers(TemplateEngine templateEngine) {
		final String prefix = "src/main/resources/templates/";

		FileTemplateResolver javaTemplateResolver = new FileTemplateResolver();
		javaTemplateResolver.setPrefix(prefix);
		javaTemplateResolver.setSuffix(".java");
		javaTemplateResolver.setOrder(1); // Set the order for this resolver

		FileTemplateResolver txtTemplateResolver = new FileTemplateResolver();
		txtTemplateResolver.setPrefix(prefix);
		txtTemplateResolver.setSuffix(".txt");
		txtTemplateResolver.setOrder(2); // Set the order for this resolver

		FileTemplateResolver htmlTemplateResolver = new FileTemplateResolver();
		htmlTemplateResolver.setPrefix(prefix);
		htmlTemplateResolver.setSuffix(".html");
		htmlTemplateResolver.setOrder(3); // Set the order for this resolver

		FileTemplateResolver xmlTemplateResolver = new FileTemplateResolver();
		xmlTemplateResolver.setPrefix(prefix);
		xmlTemplateResolver.setSuffix(".xml");
		xmlTemplateResolver.setOrder(4); // Set the order for this resolver

		FileTemplateResolver mdTemplateResolver = new FileTemplateResolver();
		mdTemplateResolver.setPrefix(prefix);
		mdTemplateResolver.setSuffix(".xml");
		mdTemplateResolver.setOrder(5); // Set the order for this resolver

		FileTemplateResolver propertiesTemplateResolver = new FileTemplateResolver();
		propertiesTemplateResolver.setPrefix(prefix);
		propertiesTemplateResolver.setSuffix(".properties");
		propertiesTemplateResolver.setOrder(6); // Set the order for this resolver

		FileTemplateResolver emptyTemplateResolver = new FileTemplateResolver();
		emptyTemplateResolver.setPrefix(prefix);
		emptyTemplateResolver.setSuffix(".properties");
		emptyTemplateResolver.setOrder(7); // Set the order for this resolver

		templateEngine.addTemplateResolver(javaTemplateResolver);
		templateEngine.addTemplateResolver(txtTemplateResolver);
		templateEngine.addTemplateResolver(htmlTemplateResolver);
		templateEngine.addTemplateResolver(xmlTemplateResolver);
		templateEngine.addTemplateResolver(mdTemplateResolver);
		templateEngine.addTemplateResolver(propertiesTemplateResolver);
		templateEngine.addTemplateResolver(emptyTemplateResolver);
	}

	private String removeFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex == -1 || lastDotIndex == 0) {
			return fileName; // No file extension found
		}
		return fileName.substring(0, lastDotIndex);
	}

	private String getFileExtension(String fileName) {
		int lastDotIndex = fileName.lastIndexOf('.');
		if (lastDotIndex == -1 || lastDotIndex == 0) {
			return ""; // No file extension found
		}
		return fileName.substring(lastDotIndex + 1);
	}
}
