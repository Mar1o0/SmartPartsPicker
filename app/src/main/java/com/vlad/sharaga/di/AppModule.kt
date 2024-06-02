package com.vlad.sharaga.di

import android.content.Context
import androidx.room.Room
import com.vlad.sharaga.data.database.AppDatabase
import com.vlad.sharaga.data.database.tables.assembly.AssemblyDao
import com.vlad.sharaga.network.api.ApiService
import com.vlad.sharaga.test.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//    @Provides
//    @Singleton
//    fun provideApiService(
//        retrofit: Retrofit
//    ): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiServiceImpl()

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