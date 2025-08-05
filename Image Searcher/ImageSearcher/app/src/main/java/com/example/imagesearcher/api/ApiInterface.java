package com.example.imagesearcher.api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface ApiInterface {
    @GET("/api")
    Call<ImageResponseModel> searchImages(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("per_page") int perPage
    );
}
