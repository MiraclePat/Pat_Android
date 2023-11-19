package com.pat.data.di

import android.content.Context
import com.pat.data.repository.image.ImageRepositoryImpl
import com.pat.data.repository.pat.PatRepositoryImpl
import com.pat.data.service.PatService
import com.pat.data.source.ImageDataSource
import com.pat.data.source.PatDataSource
import com.pat.domain.repository.PatRepository
import com.squareup.moshi.Moshi
import com.weit.domain.repository.image.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class MainModule {

    @ActivityRetainedScoped
    @Provides
    fun providePatRepository(dataSource: PatDataSource,
                             imageRepositoryImpl: ImageRepositoryImpl,
                             imageDataSource: ImageDataSource,
                             moshi: Moshi,
    ): PatRepository =
        PatRepositoryImpl(dataSource, imageRepositoryImpl, imageDataSource,moshi)

    @ActivityRetainedScoped
    @Provides
    fun providePatDataSource(service: PatService): PatDataSource =
        PatDataSource(service)

    @ActivityRetainedScoped
    @Provides
    fun providePatService(@NormalNetworkObject retrofit: Retrofit): PatService =
        retrofit.create(PatService::class.java)

    @ActivityRetainedScoped
    @Provides
    fun provideImageRepository(dataSource: ImageDataSource): ImageRepository =
        ImageRepositoryImpl(dataSource)

    @ActivityRetainedScoped
    @Provides
    fun provideImageDataSource(@ApplicationContext context: Context): ImageDataSource =
        ImageDataSource(context.contentResolver)


}
