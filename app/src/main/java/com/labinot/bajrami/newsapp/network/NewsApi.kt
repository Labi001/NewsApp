package com.labinot.bajrami.newsapp.network

import com.labinot.bajrami.newsapp.models.NewsObject
import com.labinot.bajrami.newsapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(value = "v2/everything")
    suspend fun getNews(

        @Query("q") query:String,
        @Query("from") from: String = "2024-01-04",
        @Query("to") to: String = "2024-01-04",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = Constants.API_KEY,

        ):NewsObject

}