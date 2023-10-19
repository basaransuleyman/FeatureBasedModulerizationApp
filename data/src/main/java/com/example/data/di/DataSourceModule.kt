package com.example.data.di

import com.example.data.remote.Api
import com.example.data.remote.datasource.SearchDataSource
import com.example.data.remote.datasource.SearchDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideRemoteDataSource(api: Api): SearchDataSource =
        SearchDataSourceImpl(api)
}