package com.pat.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import com.pat.data.model.ListResponse
import com.pat.data.model.auth.FirebaseTokenDTO
import com.pat.data.model.auth.KakaoCode
import com.pat.data.model.auth.KakaoToken
import com.pat.data.model.member.MyProfileContentDTO
import com.pat.data.model.member.ParticipatingContentDTO
import com.pat.data.model.member.ParticipatingDetailContentDTO
import com.pat.data.service.AuthService
import com.pat.data.service.MemberService
import com.pat.domain.model.exception.TokenNotFoundException
import com.pat.domain.model.exception.UserNotFoundException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val service: AuthService,
    private val context: Context,
    ) {

    val Context.userInfoDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "com.pat.userInfo",
    )

    private companion object{
        val KEY_USERCODE = stringPreferencesKey(name = "usercode")
        val KEY_FIREBASE_KEY = stringPreferencesKey(name = "userkey")
        val KEY_LOGIN_STATUS_KEY = booleanPreferencesKey(name = "loginstatus")
    }

    suspend fun setUserCode(userCode: String) {
        context.userInfoDataStore.edit { preference ->
            preference[KEY_USERCODE] = userCode
        }
    }

    suspend fun getUserCode(): String? {
        val flow = context.userInfoDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[KEY_USERCODE]
            }
        return flow.firstOrNull()
    }

    suspend fun setUserKey(userKey: String) {
        context.userInfoDataStore.edit { preference ->
            preference[KEY_FIREBASE_KEY] = userKey
        }
    }

    suspend fun getUserKey(): String? {
        val flow = context.userInfoDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[KEY_FIREBASE_KEY]
            }
        return flow.firstOrNull()
    }

    suspend fun setLoginStatus(loginStatus: Boolean) {
        context.userInfoDataStore.edit { preference ->
            preference[KEY_LOGIN_STATUS_KEY] = loginStatus
        }
    }

    suspend fun getLoginStatus(): Boolean? {
        val flow = context.userInfoDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[KEY_LOGIN_STATUS_KEY]
            }
        return flow.firstOrNull()
    }

    suspend fun login(kakaoToken: KakaoToken): FirebaseTokenDTO = service.login(kakaoToken)

    suspend fun loginWithFirebaseToken(customToken: String): Result<FirebaseUser> = callbackFlow {
        auth.signInWithCustomToken(customToken).addOnSuccessListener {
            val user = it.user ?: throw UserNotFoundException()
            trySend(Result.success(user))
        }.addOnFailureListener {
            trySend(Result.failure<FirebaseUser>(it))
        }
        awaitClose { /* Do Nothing */ }
    }.first()

    suspend fun register(kakaoCode: KakaoCode) {
        service.register(kakaoCode)
    }

    fun hasKakaoToken(): Boolean =
        AuthApiClient.instance.hasToken()

    fun getKakaoToken(): Flow<String?> = callbackFlow {
        UserApiClient.instance.accessTokenInfo { _, _ ->
            trySend(getAccessToken())
        }
        awaitClose {  }
    }

    private fun getAccessToken(): String =
        AuthApiClient.instance.tokenManagerProvider.manager.getToken()?.accessToken ?: throw TokenNotFoundException()

    fun getFirebaseToken(): String =
        auth.currentUser?.getIdToken(false)?.result?.token ?: throw TokenNotFoundException()


}
