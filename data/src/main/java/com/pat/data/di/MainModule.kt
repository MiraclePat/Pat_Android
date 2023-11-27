package com.pat.data.di

import android.content.Context
import com.pat.data.repository.image.ImageRepositoryImpl
import com.pat.data.repository.member.MemberRepositoryImpl
import com.pat.data.repository.pat.PatRepositoryImpl
import com.pat.data.repository.proof.ProofRepositoryImpl
import com.pat.data.service.MemberService
import com.pat.data.service.PatService
import com.pat.data.service.ProofService
import com.pat.data.source.ImageDataSource
import com.pat.data.source.MemberDataSource
import com.pat.data.source.PatDataSource
import com.pat.data.source.ProofDataSource
import com.pat.domain.repository.MemberRepository
import com.pat.domain.repository.PatRepository
import com.pat.domain.repository.ProofRepository
import com.squareup.moshi.Moshi
import com.weit.domain.repository.image.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ActivityRetainedComponent::class)
class MainModule {

    @ActivityRetainedScoped
    @Provides
    fun providePatRepository(
        dataSource: PatDataSource,
        imageRepositoryImpl: ImageRepositoryImpl,
        imageDataSource: ImageDataSource,
        moshi: Moshi,
    ): PatRepository =
        PatRepositoryImpl(dataSource, imageRepositoryImpl, imageDataSource, moshi)

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


    @ActivityRetainedScoped
    @Provides
    fun provideProofRepository(
        dataSource: ProofDataSource,
        imageRepositoryImpl: ImageRepositoryImpl,
        imageDataSource: ImageDataSource,
        moshi: Moshi,
    ): ProofRepository =
        ProofRepositoryImpl(dataSource, imageRepositoryImpl, imageDataSource, moshi)

    @ActivityRetainedScoped
    @Provides
    fun provideProofDataSource(service: ProofService): ProofDataSource =
        ProofDataSource(service)

    @ActivityRetainedScoped
    @Provides
    fun provideProofService(@NormalNetworkObject retrofit: Retrofit): ProofService =
        retrofit.create(ProofService::class.java)

    @ActivityRetainedScoped
    @Provides
    fun provideMemberRepository(dataSource: MemberDataSource): MemberRepository =
        MemberRepositoryImpl(dataSource)

    @ActivityRetainedScoped
    @Provides
    fun provideMemberDataSource(service: MemberService): MemberDataSource =
        MemberDataSource(service)

    @ActivityRetainedScoped
    @Provides
    fun provideMemberService(@NormalNetworkObject retrofit: Retrofit): MemberService =
        retrofit.create(MemberService::class.java)
}
