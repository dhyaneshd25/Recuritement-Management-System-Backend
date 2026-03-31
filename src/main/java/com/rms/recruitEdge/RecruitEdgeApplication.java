package com.rms.recruitEdge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class RecruitEdgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecruitEdgeApplication.class, args);
	}

	@Value("${spring.data.mongodb.uri}")
private String mongoUri;

@PostConstruct
public void printUri() {
    System.out.println("Mongo URI: " + mongoUri);
}
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }
}
