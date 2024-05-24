package com.vlad.sharaga.di

import com.vlad.sharaga.network.api.ApiService
import com.vlad.sharaga.network.api.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiServiceImpl()


}