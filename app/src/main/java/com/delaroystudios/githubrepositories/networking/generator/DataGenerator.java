package com.delaroystudios.githubrepositories.networking.generator;

import com.delaroystudios.githubrepositories.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DataGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .cache(null);

    private static Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat(DateFormat.LONG)
            .setPrettyPrinting()
            .setVersion(1.0)
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass, String username, String baseUrl) {
        baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("User-Agent", username)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        builder.client(httpClient.build());
        builder.baseUrl(baseUrl);
        Retrofit retrofit = builder.build();
        return  retrofit.create(serviceClass);
    }

}
