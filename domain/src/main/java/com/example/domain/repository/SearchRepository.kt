package com.example.domain.repository

import com.example.core.util.Resource
import com.example.core.model.Search

interface SearchRepository {
    suspend operator fun invoke(healthPoint: Int): Resource<Search>
}