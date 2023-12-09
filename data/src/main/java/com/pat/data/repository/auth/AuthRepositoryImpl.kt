package com.pat.data.repository.auth

import android.content.Context
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.orhanobut.logger.Logger
import com.pat.data.model.auth.FirebaseTokenDTO
import com.pat.data.model.auth.KakaoCode
import com.pat.data.model.auth.KakaoToken
import com.pat.data.source.AuthDataSource
import com.pat.data.util.exception
import com.pat.domain.model.auth.FirebaseToken
import com.pat.domain.model.exception.NeedUserRegistrationException
import com.pat.domain.model.exception.TokenNotFoundException
import com.pat.domain.model.exception.UnKnownException
import com.pat.domain.model.exception.UserNotFoundException
import com.pat.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.internal.http.HTTP_UNAUTHORIZED
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val context: Context,
    private val authDataSource: AuthDataSource,
) : AuthRepository {

    override suspend fun register(userCode: String): Result<Unit> {
        val result = runCatching {
            authDataSource.register(KakaoCode(userCode))
        }
        return if (result.isSuccess) {
            Result.success(Unit)
        } else {
            Result.failure(UnKnownException())
        }
    }

    override suspend fun logout(): Result<Unit> {
        return logoutKakao().first()
    }

    private suspend fun logoutKakao(): Flow<Result<Unit>> = callbackFlow {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                trySend(Result.failure(error))
            } else {
                trySend(Result.success(Unit))
            }
        }
        awaitClose {}
    }

    override suspend fun login(): Result<Unit> {
        val kakaoLoginResult = if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk()
        } else {
            loginWithKakaoAccount()
        }
        if (kakaoLoginResult.isFailure) {
            Logger.t("login").i("카카오로그인 실패 ${kakaoLoginResult.exception().message }")

            return Result.failure(kakaoLoginResult.exceptionOrNull() ?: Exception())
        }

        val serverLoginResult = loginToServer()
        if (serverLoginResult.isFailure) {
            Logger.t("login").i("서버로그인 실패 ${serverLoginResult.exception().message }")

            return Result.failure(serverLoginResult.exceptionOrNull() ?: Exception())
        }

        val firebaseToken = serverLoginResult.getOrNull()?.token ?: throw TokenNotFoundException()
        val firebaseLoginResult = loginWithFirebaseToken(firebaseToken)
        return if (firebaseLoginResult.isSuccess) {
            Result.success(Unit)
        } else {
            Logger.t("login").i("파이어베이스 실패${firebaseLoginResult.exception().message }")

            Result.failure(firebaseLoginResult.exceptionOrNull() ?: Exception())
        }
    }

    private fun setUserCode(userCode: String) {
        CoroutineScope(Dispatchers.IO).launch {
            authDataSource.setUserCode(userCode)
        }
    }

    override suspend fun getUsercode(): Result<String?> {
        return runCatching {
            authDataSource.getUserCode()
        }
    }

    private suspend fun loginWithKakaoTalk(): Result<Unit> = callbackFlow {
        UserApiClient.instance.loginWithKakaoTalk(context) { _, error ->
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    trySend(Result.failure(error))
                }
                CoroutineScope(Dispatchers.IO).launch {
                    trySend(loginWithKakaoAccount())
                }
            } else {
                trySend(Result.success(Unit))
            }
        }
        awaitClose {}
    }.first()

    private suspend fun loginWithKakaoAccount(): Result<Unit> = callbackFlow<Result<Unit>> {
        UserApiClient.instance.loginWithKakaoAccount(context) { _, error ->
            if (error != null) {
                trySend(Result.failure(error))
            } else {
                trySend(Result.success(Unit))
            }
        }
        awaitClose {}
    }.first()

    private suspend fun loginToServer(): Result<FirebaseToken> {
        return authDataSource.getKakaoToken().first()?.let { token ->
            val result = kotlin.runCatching {
                authDataSource.login(KakaoToken(token)).tofirebaseToken()
            }
            if (result.isSuccess) {
                Result.success(result.getOrThrow())
            } else {
                Logger.t("login").i("서버2로그인 실패 ${result.exception().message}")

                Result.failure(handleServerLoginError(result.exceptionOrNull()!!))
            }
        } ?: Result.failure(UserNotFoundException())
    }

    private suspend fun loginWithFirebaseToken(firebaseToken: String) =
        authDataSource.loginWithFirebaseToken(firebaseToken)

    private fun handleServerLoginError(t: Throwable): Throwable {
        return if (t is HttpException) {
            when (t.code()) {
                HTTP_UNAUTHORIZED -> {
                    val message = t.response()?.errorBody()?.string().toString()
                    val kakaoCode = getKakaoCode(message)
                    Logger.t("code").i("${kakaoCode}")
                    setUserCode(kakaoCode)
                    NeedUserRegistrationException()
                }
                else -> UnKnownException()
            }
        } else {
            t
        }
    }

    private fun getKakaoCode(message: String): String = JSONObject(message).getString("id").toString()

    private fun FirebaseTokenDTO.tofirebaseToken() = FirebaseToken(token)

}