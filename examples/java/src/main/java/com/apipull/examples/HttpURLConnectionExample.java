package com.apipull.examples;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * API Pull integration example using {@link java.net.HttpURLConnection}.
 *
 * <p>This legacy JDK client is useful when integrating with older Java runtimes or restricted enterprise
 * environments where third-party dependencies are not allowed.</p>
 */
public class HttpURLConnectionExample {
    public static void main(String[] args) throws Exception {
        // GET request: use this pattern for read-only API Pull endpoints.
        String getBody = request("GET", SampleConfig.GET_PATH, null);
        System.out.println("GET response:");
        System.out.println(getBody);

        // POST request: pass a JSON body when the API operation requires submitted business data.
        String postBody = request("POST", SampleConfig.POST_PATH, SampleConfig.JSON_BODY);
        System.out.println("POST response:");
        System.out.println(postBody);
    }

    private static String request(String method, String path, String body) throws Exception {
        URL url = new URL(SampleConfig.url(path));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        // Headers include JSON negotiation and optional bearer-token authentication.
        SampleConfig.defaultHeaders().forEach(connection::setRequestProperty);

        if (body != null) {
            connection.setDoOutput(true);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(body.getBytes(StandardCharsets.UTF_8));
            }
        }

        int status = connection.getResponseCode();
        InputStream responseStream;

        // Error responses are available on a separate stream in HttpURLConnection.
        if (status >= 200 && status < 400) {
            responseStream = connection.getInputStream();
        } else {
            responseStream = connection.getErrorStream();
        }
        String responseBody = responseStream == null ? "" : readFully(responseStream);
        connection.disconnect();

        return "HTTP " + status + "\n" + responseBody;
    }

    private static String readFully(InputStream inputStream) throws Exception {
        // Avoid InputStream.readAllBytes() so the example also works on older Java versions.
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(data)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }
}
