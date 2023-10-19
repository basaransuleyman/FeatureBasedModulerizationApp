package com.example.data.repository

import com.example.core.util.Resource
import com.example.data.extensions.handleAPICall
import com.example.data.mapper.SearchDataToDomainMapper
import com.example.data.remote.datasource.SearchDataSource
import com.example.core.model.Search
import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: SearchDataSource,
    private val searchMapper: SearchDataToDomainMapper
) : SearchRepository {
    override suspend fun invoke(healthPoint: Int): Resource<Search> {

        return when (val responseResource = handleAPICall { dataSource.getSearch(healthPoint) }) {
            is Resource.Success -> {
                val searchDomainModel = searchMapper.map(responseResource.data)
                Resource.Success(searchDomainModel)
            }
            is Resource.Failure -> {
                Resource.Failure(responseResource.error)
            }
        }
    }
}