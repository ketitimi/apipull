package com.apipull.examples;

import feign.Feign;
import feign.Headers;
import feign.RequestInterceptor;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * API Pull integration example using standalone OpenFeign.
 *
 * <p>This declarative style is useful when an integration team wants Java interfaces to describe remote
 * API contracts without requiring a full Spring Boot application.</p>
 */
public class FeignExample {
    public static void main(String[] args) {
        // Apply authentication once so every Feign method call carries the API Pull credential.
        RequestInterceptor authInterceptor = template -> {
            if (!SampleConfig.API_KEY.isEmpty()) {
                template.header("Authorization", "Bearer " + SampleConfig.API_KEY);
            }
        };

        // The Feign target binds the Java interface to the API Pull base URL.
        ApipullClient client = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .requestInterceptor(authInterceptor)
                .target(ApipullClient.class, SampleConfig.BASE_URL);

        System.out.println("GET response:");
        System.out.println(client.getStatus());

        System.out.println("POST response:");
        System.out.println(client.verify(SampleConfig.JSON_BODY));
    }

    interface ApipullClient {
        // Replace the placeholder path with the selected API Pull GET endpoint.
        @RequestLine("GET /api/example/status")
        @Headers("Accept: application/json")
        String getStatus();

        // Replace the placeholder path with the selected API Pull POST endpoint.
        @RequestLine("POST /api/example/verify")
        @Headers({
                "Accept: application/json",
                "Content-Type: application/json"
        })
        String verify(String requestBody);
    }
}
