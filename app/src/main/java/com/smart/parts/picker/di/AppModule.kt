package com.smart.parts.picker.di

import android.content.Context
import androidx.room.Room
import com.moczul.ok2curl.CurlInterceptor
import com.moczul.ok2curl.logger.Logger
import com.smart.parts.picker.data.database.AppDatabase
import com.smart.parts.picker.data.database.tables.assembly.AssemblyDao
import com.smart.parts.picker.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
//            .addInterceptor(
//                CurlInterceptor(
//                    object : Logger {
//                        override fun log(message: String) {
//                            android.util.Log.e("BBB", message)
//                        }
//                    }
//                )
//            )
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//
//            })
            .build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://pzt35vip.uk/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)

//    @Provides
//    @Singleton
//    fun provideApiService(): ApiService = ApiServiceImpl()

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