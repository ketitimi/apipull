package com.apipull.examples;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * API Pull integration example using Unirest.
 *
 * <p>Unirest provides a compact fluent API for teams that want readable HTTP examples in documentation,
 * demos, and small integration services.</p>
 */
public class UnirestExample {
    public static void main(String[] args) {
        // Register shared API Pull headers once for this process.
        SampleConfig.defaultHeaders().forEach((name, value) ->
                Unirest.config().setDefaultHeader(name, value)
        );

        // GET request for status, lookup, or search-style API calls.
        HttpResponse<String> getResponse = Unirest.get(SampleConfig.url(SampleConfig.GET_PATH))
                .asString();
        System.out.println("GET status: " + getResponse.getStatus());
        System.out.println(getResponse.getBody());

        // POST request with the configured JSON payload.
        HttpResponse<String> postResponse = Unirest.post(SampleConfig.url(SampleConfig.POST_PATH))
                .body(SampleConfig.JSON_BODY)
                .asString();
        System.out.println("POST status: " + postResponse.getStatus());
        System.out.println(postResponse.getBody());

        // Shut down Unirest's background resources when the application exits.
        Unirest.shutDown();
    }
}
