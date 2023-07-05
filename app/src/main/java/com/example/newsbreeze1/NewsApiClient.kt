package com.example.newsbreeze1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiClient {
    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}
