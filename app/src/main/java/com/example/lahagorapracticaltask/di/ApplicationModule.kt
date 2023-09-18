package com.example.lahagorapracticaltask.di

import android.content.Context
import androidx.annotation.NonNull
import com.example.lahagorapracticaltask.network.ApiConstants
import com.example.lahagorapracticaltask.network.ApiService
import com.example.lahagorapracticaltask.preference.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    @Singleton
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    fun provideBaseUrl() = ApiConstants.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@NonNull okHttpClient: OkHttpClient, BASE_URL: String): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context): Preferences {
        return Preferences(context)
    }

}