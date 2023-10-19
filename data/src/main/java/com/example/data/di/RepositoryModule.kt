package com.example.data.di

import com.example.data.remote.datasource.SearchDataSource
import com.example.data.repository.SearchRepositoryImpl
import com.example.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHomeRepository(
        dataSource: SearchDataSource,
    ): SearchRepository = SearchRepositoryImpl(
        dataSource
    )

}