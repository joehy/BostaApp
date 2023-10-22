package com.example.joebosta.api

import com.example.joebosta.repositry.DataRepository
import com.example.joebosta.utilites.Constant.BASEURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor()) // Add the custom Interceptor
        .build()
    @Provides
    @Singleton
    fun provideRetrofit(): NetworkService =
         Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
             .build()
            .create(NetworkService::class.java)

}