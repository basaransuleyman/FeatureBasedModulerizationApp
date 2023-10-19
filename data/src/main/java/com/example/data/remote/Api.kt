package com.example.data.remote

import com.example.data.model.SearchResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/v1/cards")
    suspend fun getSearch(
        @Query("hp") healthPoint: String,
    ): Response<SearchResponse>
}