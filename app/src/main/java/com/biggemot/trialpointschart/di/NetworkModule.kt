package com.biggemot.trialpointschart.di

import com.biggemot.trialpointschart.BuildConfig
import com.biggemot.trialpointschart.data.ChartApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    companion object {

        private const val BASE_URL = "https://hr-challenge.interactivestandard.com/"

        @Provides
        fun provideClient(): OkHttpClient {
            return OkHttpClient.Builder().apply {
                addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
                    )
                )
            }.build()
        }

        @Provides
        fun provideGson(): Gson {
            return GsonBuilder().create()
        }

        @Provides
        fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(client)
            .build()

        @Provides
        fun provideChartApi(retrofit: Retrofit): ChartApi = retrofit.create(ChartApi::class.java)
    }
}