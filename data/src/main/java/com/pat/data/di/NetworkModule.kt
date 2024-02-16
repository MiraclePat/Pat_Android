package com.pat.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.orhanobut.logger.Logger
import com.pat.data.BuildConfig
import com.pat.data.interceptor.AuthInterceptor
import com.pat.data.source.AuthDataSource
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


private const val BASE_URL = "https://miraclepat.site/"
private const val BASE_URL_NAVER_API = "https://openapi.naver.com/"
private const val BASE_URL_NAVER_GEOCODE_API = "https://naveropenapi.apigw.ntruss.com/"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormalNetworkObject

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthNetworkObject

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
        run {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
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
        run {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id", BuildConfig.CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", BuildConfig.CLIENT_SECRET)
                    .build()

                Logger.t("MainTest").i("$request")
                return@Interceptor it.proceed(request)
            }

            OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
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
       run {
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

    @AuthNetworkObject
    @Singleton
    @Provides
    fun provideAuthOkHttpClient(
        authInterceptor: AuthInterceptor,
    ): OkHttpClient =
        run {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        }

    @AuthNetworkObject
    @Singleton
    @Provides
    fun provideAuthRetrofit(
        @AuthNetworkObject okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthInterceptor(
        dataSource: AuthDataSource,
    ): AuthInterceptor = AuthInterceptor(dataSource)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}
