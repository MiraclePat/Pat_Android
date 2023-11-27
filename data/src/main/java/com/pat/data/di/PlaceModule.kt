package com.pat.data.di

import com.pat.data.repository.place.PlaceRepositoryImpl
import com.pat.data.service.PlaceService
import com.pat.data.source.PlaceDataSource
import com.pat.domain.repository.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PlaceModule {

    @Singleton
    @Provides
    fun providePlaceService(@NaverNetworkObject retrofit: Retrofit): PlaceService =
        retrofit.create(PlaceService::class.java)

    @Singleton
    @Provides
    fun providePlaceDataSource(
        service: PlaceService,
    ): PlaceDataSource = PlaceDataSource(service)

    @Singleton
    @Provides
    fun providePlaceRepository(
        dataSource: PlaceDataSource
    ): PlaceRepository =
        PlaceRepositoryImpl(dataSource)
}