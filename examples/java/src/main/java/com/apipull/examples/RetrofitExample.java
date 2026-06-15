package com.apipull.examples;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * API Pull integration example using Retrofit.
 *
 * <p>Retrofit is common in Android, mobile-backend, and client SDK projects where remote APIs are modeled
 * as typed Java interfaces.</p>
 */
public class RetrofitExample {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void main(String[] args) throws Exception {
        // Centralize API Pull headers through an OkHttp interceptor.
        Interceptor authInterceptor = chain -> {
            okhttp3.Request.Builder builder = chain.request().newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json");
            if (!SampleConfig.API_KEY.isEmpty()) {
                builder.header("Authorization", "Bearer " + SampleConfig.API_KEY);
            }
            return chain.proceed(builder.build());
        };

        // Retrofit uses OkHttp underneath, so timeouts, retries, and logging can be configured here.
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build();

        // Retrofit baseUrl must end with a trailing slash.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SampleConfig.BASE_URL + "/")
                .client(okHttpClient)
                .build();

        ApipullApi api = retrofit.create(ApipullApi.class);

        // GET request through the typed API interface.
        retrofit2.Response<ResponseBody> getResponse = api.getStatus().execute();
        System.out.println("GET status: " + getResponse.code());
        System.out.println(getResponse.body() == null ? "" : getResponse.body().string());

        // POST request with a raw JSON RequestBody for maximum copy-paste compatibility.
        RequestBody requestBody = RequestBody.create(JSON, SampleConfig.JSON_BODY);
        retrofit2.Response<ResponseBody> postResponse = api.verify(requestBody).execute();
        System.out.println("POST status: " + postResponse.code());
        System.out.println(postResponse.body() == null ? "" : postResponse.body().string());
    }

    interface ApipullApi {
        // Replace this placeholder path with the API Pull GET endpoint you want to call.
        @GET("api/example/status")
        Call<ResponseBody> getStatus();

        // Replace this placeholder path with the API Pull POST endpoint you want to call.
        @POST("api/example/verify")
        @Headers("Content-Type: application/json")
        Call<ResponseBody> verify(@Body RequestBody requestBody);
    }
}
