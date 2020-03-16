package com.mahim.accessingdataneo4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableNeo4jRepositories
public class AccessingDataNeo4jApplication {
	private static final Logger log = LoggerFactory.getLogger(AccessingDataNeo4jApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataNeo4jApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(PersonRepository personRepository) {
		return args -> {
			personRepository.deleteAll();

			Person mahim = new Person("mahim");
			Person nitol = new Person("nitol");
			Person ornob = new Person("ornob");
			List<Person> team = Arrays.asList(mahim, nitol, ornob);
			log.info("Before linking with neo4j....!");
			team.stream().forEach(person -> log.info("\t" + person.toString()));

			personRepository.save(mahim);
			personRepository.save(nitol);
			personRepository.save(ornob);

			mahim = personRepository.findByName(mahim.getName());
			mahim.worksWith(nitol);
			mahim.worksWith(ornob);
			personRepository.save(mahim);

			nitol = personRepository.findByName(nitol.getName());
			nitol.worksWith(ornob);
			personRepository.save(nitol);

			log.info("Look up each person by name: ");
			team.stream().forEach(person -> {
				log.info("\t" + personRepository.findByName(person.getName()).toString());
			});
		};
	}
}
