package com.salat23.wafflesproject;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

	@Bean
	public Cloudinary cloudinaryConfig() {
		String api_secret = System.getenv("cloudinary_api_secret");
		String api_key = System.getenv("cloudinary_api_key");
		String api_name = System.getenv("cloudinary_api_name");
		Cloudinary cloudinary;
		Map<String, String> config = new HashMap<>();
		config.put("cloud_name", api_name);
		config.put("api_key", api_key);
		config.put("api_secret", api_secret);
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
