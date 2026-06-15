# API Pull HTTP Client Examples

This directory provides copy-ready HTTP client examples for API Pull integrations.

Default base URL:

```text
https://www.apipull.com
```

The sample endpoint paths are placeholders:

- GET: `/api/example/status`
- POST: `/api/example/verify`

Replace them with the actual API path from API Pull documentation or the API Hub before using the code in production.

## Runtime Configuration

All examples use the same optional environment variables:

```bash
export APIPULL_BASE_URL="https://www.apipull.com"
export APIPULL_API_KEY="your_api_key"
export APIPULL_GET_PATH="/api/example/status"
export APIPULL_POST_PATH="/api/example/verify"
```

If `APIPULL_API_KEY` is set, the examples send it as:

```text
Authorization: Bearer your_api_key
```

## Java Examples

Java source files are under `examples/java/src/main/java/com/apipull/examples`.

| Client | Source File | Dependency Notes |
| --- | --- | --- |
| Java native `java.net.http.HttpClient` | `JavaNetHttpClientExample.java` | JDK 11+ |
| Java native `java.net.HttpURLConnection` | `HttpURLConnectionExample.java` | JDK standard library |
| OkHttp | `OkHttpExample.java` | `com.squareup.okhttp3:okhttp` |
| Apache HttpClient | `ApacheHttpClientExample.java` | `org.apache.httpcomponents:httpclient` |
| AsyncHttpClient | `AsyncHttpClientExample.java` | `org.asynchttpclient:async-http-client` |
| Hutool HTTP | `HutoolHttpExample.java` | `cn.hutool:hutool-http` |
| Spring WebClient | `SpringWebClientExample.java` | `org.springframework.boot:spring-boot-starter-webflux` |
| Spring RestTemplate | `SpringRestTemplateExample.java` | `org.springframework.boot:spring-boot-starter-web` |
| Standalone Feign | `FeignExample.java` | `io.github.openfeign:feign-core`, `io.github.openfeign:feign-gson` |
| Spring Cloud OpenFeign | `SpringCloudOpenFeignExample.java` | `org.springframework.cloud:spring-cloud-starter-openfeign` |
| Retrofit | `RetrofitExample.java` | `com.squareup.retrofit2:retrofit` |
| Unirest | `UnirestExample.java` | `com.konghq:unirest-java` |

For Spring-based examples, Spring Boot `2.0.9.RELEASE` can be used. When adding Spring Cloud OpenFeign to a Spring Boot 2.0.x project, use the Spring Cloud release train compatible with your dependency management.

## Python Example

Python source file:

```text
examples/python/apipull_requests_example.py
```

Install dependency:

```bash
pip install requests
```

Run:

```bash
python examples/python/apipull_requests_example.py
```
