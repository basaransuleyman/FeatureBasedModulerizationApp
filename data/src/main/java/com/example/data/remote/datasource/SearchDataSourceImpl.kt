package com.example.data.remote.datasource

import com.example.data.model.SearchResponse
import com.example.data.remote.Api
import retrofit2.Response
import javax.inject.Inject

internal class SearchDataSourceImpl @Inject constructor(
    private val api: Api
) : SearchDataSource {
    override suspend fun getSearch(healthPoint: Int): Response<SearchResponse> {
        return api.getSearch("gte$healthPoint")
    }
}