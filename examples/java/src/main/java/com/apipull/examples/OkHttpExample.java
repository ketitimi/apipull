package com.apipull.examples;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * API Pull integration example using OkHttp.
 *
 * <p>OkHttp is a strong default for commercial Java services because it provides connection pooling,
 * interceptors, timeouts, and mature TLS behavior.</p>
 */
public class OkHttpExample {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void main(String[] args) throws Exception {
        // Reuse one OkHttpClient per application to benefit from connection pooling.
        OkHttpClient client = new OkHttpClient();

        // Build a GET request with shared API Pull headers.
        Request.Builder getBuilder = new Request.Builder()
                .url(SampleConfig.url(SampleConfig.GET_PATH))
                .get();
        SampleConfig.defaultHeaders().forEach(getBuilder::addHeader);

        try (Response response = client.newCall(getBuilder.build()).execute()) {
            System.out.println("GET status: " + response.code());
            System.out.println(response.body() == null ? "" : response.body().string());
        }

        // Build a POST request with the JSON payload required by the target API.
        RequestBody requestBody = RequestBody.create(JSON, SampleConfig.JSON_BODY);
        Request.Builder postBuilder = new Request.Builder()
                .url(SampleConfig.url(SampleConfig.POST_PATH))
                .post(requestBody);
        SampleConfig.defaultHeaders().forEach(postBuilder::addHeader);

        try (Response response = client.newCall(postBuilder.build()).execute()) {
            System.out.println("POST status: " + response.code());
            System.out.println(response.body() == null ? "" : response.body().string());
        }
    }
}
