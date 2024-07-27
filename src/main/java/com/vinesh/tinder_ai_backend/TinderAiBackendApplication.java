package com.vinesh.tinder_ai_backend;

import com.vinesh.tinder_ai_backend.profiles.Gender;
import com.vinesh.tinder_ai_backend.profiles.Profile;
import com.vinesh.tinder_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TinderAiBackendApplication  implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Profile profile = new Profile(
				"1",
				"Vinesh",
				"Kumar",
				25,
				"Indian",
				Gender.MALE,
				"Software Engineer",
				"foo.jpg",
				"INTP"
		);
		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);
	}

}
