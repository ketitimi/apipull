package com.apipull.examples;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * API Pull integration example using the JDK 11+ {@link java.net.http.HttpClient}.
 *
 * <p>This is the recommended native Java client for modern server applications that do not want an
 * additional HTTP dependency.</p>
 */
public class JavaNetHttpClientExample {
    public static void main(String[] args) throws Exception {
        // HttpClient is thread-safe and can be reused across requests in production services.
        HttpClient client = HttpClient.newHttpClient();

        // GET is typically used for status checks, lookups, search, and read-only API queries.
        HttpRequest.Builder getBuilder = HttpRequest.newBuilder()
                .uri(URI.create(SampleConfig.url(SampleConfig.GET_PATH)))
                .GET();
        SampleConfig.defaultHeaders().forEach(getBuilder::header);

        HttpResponse<String> getResponse = client.send(
                getBuilder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
        System.out.println("GET status: " + getResponse.statusCode());
        System.out.println(getResponse.body());

        // POST sends a JSON payload for verification, enrichment, onboarding, or transactional workflows.
        HttpRequest.Builder postBuilder = HttpRequest.newBuilder()
                .uri(URI.create(SampleConfig.url(SampleConfig.POST_PATH)))
                .POST(HttpRequest.BodyPublishers.ofByteArray(SampleConfig.jsonBytes()));
        SampleConfig.defaultHeaders().forEach(postBuilder::header);

        HttpResponse<String> postResponse = client.send(
                postBuilder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
        System.out.println("POST status: " + postResponse.statusCode());
        System.out.println(postResponse.body());
    }
}
