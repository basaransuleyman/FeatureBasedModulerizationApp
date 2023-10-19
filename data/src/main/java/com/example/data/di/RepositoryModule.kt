package com.example.data.di

import com.example.data.mapper.CardMapper
import com.example.data.mapper.SearchDataToDomainMapper
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
        searchDataToDomainMapper: SearchDataToDomainMapper
    ): SearchRepository = SearchRepositoryImpl(
        dataSource,
        searchDataToDomainMapper
    )

    @Singleton
    @Provides
    fun provideSearchDataToDomainMapper(cardMapper: CardMapper): SearchDataToDomainMapper {
        return SearchDataToDomainMapper(cardMapper)
    }
}