package com.apipull.examples;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * API Pull integration example using Apache HttpClient 4.x.
 *
 * <p>This client is common in long-lived enterprise Java systems and integrates well with custom
 * connection managers, proxy settings, and corporate TLS policies.</p>
 */
public class ApacheHttpClientExample {
    public static void main(String[] args) throws Exception {
        // CloseableHttpClient should be closed when the application no longer needs it.
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // GET request for read-only API Pull operations.
            HttpGet get = new HttpGet(SampleConfig.url(SampleConfig.GET_PATH));
            SampleConfig.defaultHeaders().forEach(get::setHeader);

            try (CloseableHttpResponse response = client.execute(get)) {
                System.out.println("GET status: " + response.getStatusLine().getStatusCode());
                System.out.println(readBody(response.getEntity()));
            }

            // POST request for JSON-based verification or data submission workflows.
            HttpPost post = new HttpPost(SampleConfig.url(SampleConfig.POST_PATH));
            SampleConfig.defaultHeaders().forEach(post::setHeader);
            post.setEntity(new StringEntity(SampleConfig.JSON_BODY, ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = client.execute(post)) {
                System.out.println("POST status: " + response.getStatusLine().getStatusCode());
                System.out.println(readBody(response.getEntity()));
            }
        }
    }

    private static String readBody(HttpEntity entity) throws Exception {
        // EntityUtils consumes and releases the response entity content.
        return entity == null ? "" : EntityUtils.toString(entity);
    }
}
