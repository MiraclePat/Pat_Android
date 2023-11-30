package com.pat.data.di

import com.pat.data.repository.place.CoordinateRepositoryImpl
import com.pat.data.repository.place.PlaceRepositoryImpl
import com.pat.data.service.CoordinateService
import com.pat.data.service.PlaceService
import com.pat.data.source.CoordinateDataSource
import com.pat.data.source.PlaceDataSource
import com.pat.domain.repository.CoordinateRepository
import com.pat.domain.repository.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CoordinateModule {

    @Singleton
    @Provides
    fun provideCoordinateService(@NaverGeocodeNetworkObject retrofit: Retrofit): CoordinateService =
        retrofit.create(CoordinateService::class.java)

    @Singleton
    @Provides
    fun provideCoordinateDataSource(
        service: CoordinateService,
    ): CoordinateDataSource = CoordinateDataSource(service)

    @Singleton
    @Provides
    fun provideCoordinateRepository(
        dataSource: CoordinateDataSource
    ): CoordinateRepository =
        CoordinateRepositoryImpl(dataSource)
}