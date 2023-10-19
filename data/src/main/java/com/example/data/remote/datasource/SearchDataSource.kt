package com.example.data.remote.datasource

import com.example.data.model.SearchResponse
import retrofit2.Response

interface SearchDataSource {
    suspend fun getSearch(healthPoint: Int): Response<SearchResponse>
}