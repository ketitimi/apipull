package com.apipull.examples;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * API Pull integration example using Hutool HTTP.
 *
 * <p>Hutool is popular in lightweight Java applications that prefer a concise utility-style API for
 * simple HTTP calls.</p>
 */
public class HutoolHttpExample {
    public static void main(String[] args) {
        // GET request with API Pull headers applied in one chain.
        try (HttpResponse getResponse = HttpRequest.get(SampleConfig.url(SampleConfig.GET_PATH))
                .addHeaders(SampleConfig.defaultHeaders())
                .execute()) {
            System.out.println("GET status: " + getResponse.getStatus());
            System.out.println(getResponse.body());
        }

        // POST request sends the configured JSON payload to the selected API endpoint.
        try (HttpResponse postResponse = HttpRequest.post(SampleConfig.url(SampleConfig.POST_PATH))
                .addHeaders(SampleConfig.defaultHeaders())
                .body(SampleConfig.JSON_BODY)
                .execute()) {
            System.out.println("POST status: " + postResponse.getStatus());
            System.out.println(postResponse.body());
        }
    }
}
