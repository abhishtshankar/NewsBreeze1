package com.example.newsbreeze1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository {
    private val api: NewsApiClient by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(NewsApiClient::class.java)
    }

    suspend fun getBreakingNews(): List<Article>? {
        val response = api.getBreakingNews("in", "ab04d6baa4864a8982ab424144151d69")
        if (response.isSuccessful) {
            return response.body()?.articles
        }
        return null
    }
}
