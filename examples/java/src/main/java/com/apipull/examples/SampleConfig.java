package com.apipull.examples;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Shared configuration used by all API Pull HTTP client examples.
 *
 * <p>Commercial integrations should keep host, credentials, endpoint paths, and sample payloads outside
 * source code. These values can be injected from environment variables in local development, CI, or
 * production runtime.</p>
 */
final class SampleConfig {
    // API Pull public gateway. Override APIPULL_BASE_URL only for staging, QA, or private deployments.
    static final String BASE_URL = env("APIPULL_BASE_URL", "https://www.apipull.com");

    // Keep API keys out of source control. Public examples read the token from the environment.
    static final String API_KEY = env("APIPULL_API_KEY", "");

    // Replace these placeholder paths with the concrete API path selected from API Pull documentation.
    static final String GET_PATH = env("APIPULL_GET_PATH", "/api/example/status");
    static final String POST_PATH = env("APIPULL_POST_PATH", "/api/example/verify");

    // The default JSON body is intentionally small so it can be copied into demos, tests, and docs.
    static final String JSON_BODY = env(
            "APIPULL_JSON_BODY",
            "{\"requestId\":\"demo-001\",\"name\":\"API Pull Demo\"}"
    );

    private SampleConfig() {
    }

    static String url(String path) {
        return BASE_URL + path;
    }

    static Map<String, String> defaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");

        // Most commercial APIs use bearer tokens or API keys; adapt this line if your plan uses another scheme.
        if (!API_KEY.isEmpty()) {
            headers.put("Authorization", "Bearer " + API_KEY);
        }
        return Collections.unmodifiableMap(headers);
    }

    static byte[] jsonBytes() {
        return JSON_BODY.getBytes(StandardCharsets.UTF_8);
    }

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.trim().isEmpty() ? fallback : value;
    }
}
