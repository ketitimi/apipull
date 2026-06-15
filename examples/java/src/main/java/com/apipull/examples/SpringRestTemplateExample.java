package com.apipull.examples;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * API Pull integration example using Spring RestTemplate.
 *
 * <p>RestTemplate remains common in Spring Boot 2.0.x business systems and is easy to integrate with
 * existing synchronous service code.</p>
 */
public class SpringRestTemplateExample {
    public static void main(String[] args) {
        // In production, define RestTemplate as a Spring bean so timeouts and interceptors are centralized.
        RestTemplate restTemplate = new RestTemplate();

        // Convert shared API Pull headers into Spring's HttpHeaders container.
        HttpHeaders headers = new HttpHeaders();
        SampleConfig.defaultHeaders().forEach(headers::set);

        // GET request using exchange so headers and response metadata are visible.
        ResponseEntity<String> getResponse = restTemplate.exchange(
                SampleConfig.url(SampleConfig.GET_PATH),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
        System.out.println("GET status: " + getResponse.getStatusCodeValue());
        System.out.println(getResponse.getBody());

        // POST request with a JSON body and the same authentication headers.
        ResponseEntity<String> postResponse = restTemplate.exchange(
                SampleConfig.url(SampleConfig.POST_PATH),
                HttpMethod.POST,
                new HttpEntity<>(SampleConfig.JSON_BODY, headers),
                String.class
        );
        System.out.println("POST status: " + postResponse.getStatusCodeValue());
        System.out.println(postResponse.getBody());
    }
}
