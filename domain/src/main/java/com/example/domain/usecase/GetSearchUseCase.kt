package com.example.domain.usecase

import com.example.core.util.Resource
import com.example.core.model.Search

interface GetSearchUseCase {
    suspend operator fun invoke(healthPoint: Int): Resource<Search>
}