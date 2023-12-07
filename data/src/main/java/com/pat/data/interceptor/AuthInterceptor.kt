package com.pat.data.interceptor

import com.orhanobut.logger.Logger
import com.pat.data.source.AuthDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataSource: AuthDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorization = BEARER_PREFIX + dataSource.getFirebaseToken()
        val newRequest = chain.request().newBuilder().apply {
            addHeader(AUTHORIZATION_HEADER, authorization)
        }
        return chain.proceed(newRequest.build())
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }
}