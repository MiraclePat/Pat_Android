package com.weit.data.di

import com.pat.data.repository.HomeRepositoryImpl
import com.pat.data.repository.PatRepositoryImpl
import com.pat.data.service.HomeService
import com.pat.data.service.PatService
import com.pat.data.source.HomeDataSource
import com.pat.data.source.PatDataSource
import com.pat.domain.repository.HomeRepository
import com.pat.domain.repository.PatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class MainModule {
    @ActivityRetainedScoped
    @Provides
    fun provideHomeRepository(dataSource: HomeDataSource): HomeRepository =
        HomeRepositoryImpl(dataSource)

    @ActivityRetainedScoped
    @Provides
    fun provideHomeDataSource(service: HomeService): HomeDataSource =
        HomeDataSource(service)

    @ActivityRetainedScoped
    @Provides
    fun provideHomeService(@NormalNetworkObject retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @ActivityRetainedScoped
    @Provides
    fun providePatRepository(dataSource: PatDataSource): PatRepository =
        PatRepositoryImpl(dataSource)

    @ActivityRetainedScoped
    @Provides
    fun providePatDataSource(service: PatService): PatDataSource =
        PatDataSource(service)

    @ActivityRetainedScoped
    @Provides
    fun providePatService(@NormalNetworkObject retrofit: Retrofit): PatService =
        retrofit.create(PatService::class.java)

}
