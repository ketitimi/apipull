package com.apipull.examples;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * API Pull integration example using Spring WebClient.
 *
 * <p>WebClient is suitable for Spring Boot 2.x applications that prefer reactive APIs or need
 * non-blocking outbound HTTP calls.</p>
 */
public class SpringWebClientExample {
    public static void main(String[] args) {
        // Configure the API Pull base URL and shared headers once at client construction time.
        WebClient webClient = WebClient.builder()
                .baseUrl(SampleConfig.BASE_URL)
                .defaultHeaders(headers -> SampleConfig.defaultHeaders().forEach(headers::set))
                .build();

        // GET request for read-only API Pull endpoints.
        String getResponse = webClient.get()
                .uri(SampleConfig.GET_PATH)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("GET response:");
        System.out.println(getResponse);

        // POST request with a JSON body. Replace SampleConfig.JSON_BODY with your domain payload.
        String postResponse = webClient.post()
                .uri(SampleConfig.POST_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(SampleConfig.JSON_BODY))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("POST response:");
        System.out.println(postResponse);
    }
}
