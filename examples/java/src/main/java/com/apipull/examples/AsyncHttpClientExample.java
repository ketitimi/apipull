package com.apipull.examples;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;

import static org.asynchttpclient.Dsl.asyncHttpClient;

/**
 * API Pull integration example using AsyncHttpClient.
 *
 * <p>Use this style when a service needs non-blocking HTTP calls, concurrent verification requests,
 * or high-throughput API fan-out.</p>
 */
public class AsyncHttpClientExample {
    public static void main(String[] args) {
        // AsyncHttpClient manages event-loop resources, so close it when the service shuts down.
        try (AsyncHttpClient client = asyncHttpClient()) {
            // Execute the GET request asynchronously and join only for this compact demo.
            CompletableFuture<Response> getFuture = client.prepareGet(SampleConfig.url(SampleConfig.GET_PATH))
                    .setHeaders(SampleConfig.defaultHeaders())
                    .execute()
                    .toCompletableFuture();

            Response getResponse = getFuture.join();
            System.out.println("GET status: " + getResponse.getStatusCode());
            System.out.println(getResponse.getResponseBody());

            // POST uses the same shared headers and sends the configured JSON payload.
            CompletableFuture<Response> postFuture = client.preparePost(SampleConfig.url(SampleConfig.POST_PATH))
                    .setHeaders(SampleConfig.defaultHeaders())
                    .setBody(SampleConfig.JSON_BODY)
                    .execute()
                    .toCompletableFuture();

            Response postResponse = postFuture.join();
            System.out.println("POST status: " + postResponse.getStatusCode());
            System.out.println(postResponse.getResponseBody());
        } catch (Exception e) {
            throw new RuntimeException("AsyncHttpClient request failed", e);
        }
    }
}
