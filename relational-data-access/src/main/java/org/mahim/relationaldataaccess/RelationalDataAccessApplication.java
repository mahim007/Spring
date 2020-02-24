package org.mahim.relationaldataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationalDataAccessApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RelationalDataAccessApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Override
	public void run(String... args) throws Exception {
		log.info("Creating tables");
		jdbcTemplate.execute("DROP table customers if exists ");
		jdbcTemplate.execute("create table customers (id serial, first_name varchar(20), last_name varchar(20))");

		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
				.stream().map(name -> name.split(" "))
				.collect(Collectors.toList());
		splitUpNames.forEach(name -> log.info(String.format("custome %s %s", name[0], name[1])));
		jdbcTemplate.batchUpdate("INSERT into customers (first_name, last_name) values (?, ?)", splitUpNames);
		log.info("querying for customers where first_name=josh");
		jdbcTemplate.query("select id, first_name, last_name from customers where first_name=? ", new Object[]{"Josh"},
				(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")))
				.forEach(customer -> log.info(customer.toString()));
	}
}
