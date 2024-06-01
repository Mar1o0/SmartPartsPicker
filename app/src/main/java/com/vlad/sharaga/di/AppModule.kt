package com.vlad.sharaga.di

import android.content.Context
import androidx.room.Room
import com.vlad.sharaga.data.database.AppDatabase
import com.vlad.sharaga.data.database.tables.assembly.AssemblyDao
import com.vlad.sharaga.network.api.ApiService
import com.vlad.sharaga.network.api.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApiService(
        @ApplicationContext context: Context
    ): ApiService = ApiServiceImpl(context)

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context = context,
        klass = AppDatabase::class.java,
        name = "app_database"
    ).build()

    @Provides
    fun provideAssemblyDao(
        database: AppDatabase
    ): AssemblyDao = database.assemblyDao()

}