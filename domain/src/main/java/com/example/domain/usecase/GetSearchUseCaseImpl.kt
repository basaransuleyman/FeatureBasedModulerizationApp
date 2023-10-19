package com.example.domain.usecase

import com.example.core.di.IoDispatcher
import com.example.core.util.Resource
import com.example.core.model.Search
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSearchUseCaseImpl @Inject constructor(
    private val repository: SearchRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GetSearchUseCase {
    override suspend operator fun invoke(healthPoint: Int): Resource<Search> {
        return withContext(dispatcher) {
            when (val resource = repository.invoke(healthPoint)) {
                is Resource.Success -> {
                    Resource.Success(resource.data)
                }

                is Resource.Failure -> {
                    Resource.Failure(resource.error)
                }
            }
        }
    }
}