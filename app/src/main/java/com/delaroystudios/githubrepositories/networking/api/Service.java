package com.delaroystudios.githubrepositories.networking.api;


import com.delaroystudios.githubrepositories.model.RepositoriesResponse;
import com.delaroystudios.githubrepositories.networking.Routes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET(Routes.SEARCH_REPO)
    Call<RepositoriesResponse> fetchRepo(@Query("q") String queryvalue);
}
