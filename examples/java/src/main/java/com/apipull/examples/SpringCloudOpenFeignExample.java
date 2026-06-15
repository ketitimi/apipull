package com.apipull.examples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * API Pull integration example using Spring Cloud OpenFeign.
 *
 * <p>This is the most natural Feign option for Spring Boot services that want declarative API clients,
 * dependency injection, and centralized configuration. Spring Boot 2.0.9 projects should use a compatible
 * Spring Cloud release train.</p>
 */
@SpringBootApplication
@EnableFeignClients
public class SpringCloudOpenFeignExample implements CommandLineRunner {
    private final ApipullFeignClient client;

    public SpringCloudOpenFeignExample(ApipullFeignClient client) {
        this.client = client;
    }

    public static void main(String[] args) {
        // Starts a minimal Spring Boot application that immediately runs the sample calls.
        SpringApplication.run(SpringCloudOpenFeignExample.class, args);
    }

    @Override
    public void run(String... args) {
        // Headers are passed explicitly so credentials are visible in the example.
        Map<String, String> headers = SampleConfig.defaultHeaders();

        // GET request through the injected OpenFeign client.
        String getResponse = client.getStatus(headers);
        System.out.println("GET response:");
        System.out.println(getResponse);

        // POST request through the same OpenFeign client.
        String postResponse = client.verify(headers, SampleConfig.JSON_BODY);
        System.out.println("POST response:");
        System.out.println(postResponse);
    }

    // Replace the placeholder mapping paths with the API Pull endpoints used by your product workflow.
    @FeignClient(name = "apipull", url = "${apipull.base-url:https://www.apipull.com}")
    interface ApipullFeignClient {
        @GetMapping(value = "/api/example/status", produces = "application/json")
        String getStatus(@RequestHeader Map<String, String> headers);

        @PostMapping(value = "/api/example/verify", consumes = "application/json", produces = "application/json")
        String verify(@RequestHeader Map<String, String> headers, @RequestBody String requestBody);
    }
}
