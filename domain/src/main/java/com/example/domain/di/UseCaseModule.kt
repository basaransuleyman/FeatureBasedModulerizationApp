package com.example.domain.di

import com.example.core.di.IoDispatcher
import com.example.domain.repository.SearchRepository
import com.example.domain.usecase.GetSearchUseCase
import com.example.domain.usecase.GetSearchUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideSearchUseCase(
        getHomeRepository: SearchRepository,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): GetSearchUseCase = GetSearchUseCaseImpl(
        getHomeRepository,
        dispatcher
    )

}