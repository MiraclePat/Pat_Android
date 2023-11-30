package com.pat.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pat.data.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor


private const val BASE_URL = "http://43.200.73.58:8080/"
private const val BASE_URL_NAVER_API = "https://openapi.naver.com/"
private const val BASE_URL_NAVER_GEOCODE_API = "https://naveropenapi.apigw.ntruss.com/"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormalNetworkObject

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NaverNetworkObject

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NaverGeocodeNetworkObject

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @NormalNetworkObject
    @Singleton
    @Provides
    fun provideNormalOkHttpClient(): OkHttpClient =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .build()
        }

    @NormalNetworkObject
    @Singleton
    @Provides
    fun provideNormalRetrofit(
        @NormalNetworkObject okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @NaverNetworkObject
    @Singleton
    @Provides
    fun provideNaverOkHttpClient(): OkHttpClient =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id", BuildConfig.CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", BuildConfig.CLIENT_SECRET)
                    .build()
                return@Interceptor it.proceed(request)
            }

            OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .build()
        }

    @NaverNetworkObject
    @Singleton
    @Provides
    fun provideNaverRetrofit(
        @NaverNetworkObject okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_NAVER_API)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @NaverGeocodeNetworkObject
    @Singleton
    @Provides
    fun provideNaverGeocodeOkHttpClient(): OkHttpClient =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-NCP-APIGW-API-KEY-ID", BuildConfig.GEOCODE_CLIENT_ID)
                    .addHeader("X-NCP-APIGW-API-KEY", BuildConfig.GEOCODE_CLIENT_SECRET)
                    .build()
                return@Interceptor it.proceed(request)
            }

            OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .build()
        }

    @NaverGeocodeNetworkObject
    @Singleton
    @Provides
    fun provideNaverGeocodeRetrofit(
        @NaverGeocodeNetworkObject okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL_NAVER_GEOCODE_API)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}
