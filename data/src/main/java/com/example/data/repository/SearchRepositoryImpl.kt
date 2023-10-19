package com.example.data.repository

import com.example.core.util.Resource
import com.example.data.extensions.handleAPICall
import com.example.data.remote.datasource.SearchDataSource
import com.example.core.model.Search
import com.example.data.mapper.toSearch
import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: SearchDataSource
) : SearchRepository {
    override suspend fun invoke(healthPoint: Int): Resource<Search> {

        return when (val responseResource = handleAPICall { dataSource.getSearch(healthPoint) }) {
            is Resource.Success -> {
                val searchDomainModel = responseResource.data.toSearch()
                Resource.Success(searchDomainModel)
            }

            is Resource.Failure -> {
                Resource.Failure(responseResource.error)
            }
        }
    }
}