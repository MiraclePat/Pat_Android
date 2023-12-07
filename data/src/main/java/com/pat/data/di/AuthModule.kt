package com.pat.data.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.pat.data.repository.auth.AuthRepositoryImpl
import com.pat.data.service.AuthService
import com.pat.data.source.AuthDataSource
import com.pat.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun providesAuthRepository(
        @ApplicationContext context: Context,
        authDataSource: AuthDataSource,
    ): AuthRepository = AuthRepositoryImpl(context, authDataSource)

    @Singleton
    @Provides
    fun providesAuthService(
        @NormalNetworkObject retrofit: Retrofit,
    ): AuthService = retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun providesAuthDataSource(
        auth: FirebaseAuth,
        service: AuthService,
        @ApplicationContext context: Context
    ): AuthDataSource = AuthDataSource(auth, service,context)

    @Singleton
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
}
