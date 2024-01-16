package com.pat.data.repository.member

import com.orhanobut.logger.Logger
import com.pat.data.model.member.NicknameRequestBody
import com.pat.data.repository.image.ImageRepositoryImpl
import com.pat.data.source.ImageDataSource
import com.pat.data.source.MemberDataSource
import com.pat.data.util.exception
import com.pat.domain.model.exception.InvaildRequestException
import com.pat.domain.model.exception.UnKnownException
import com.pat.domain.model.exception.UserNotFoundException
import com.pat.domain.model.member.MyProfileContent
import com.pat.domain.model.member.OpenPatRequestInfo
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingDetailContent
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.repository.MemberRepository
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.http.HTTP_CONFLICT
import okhttp3.internal.http.HTTP_NOT_FOUND
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberDataSource: MemberDataSource,
    private val imageRepositoryImpl: ImageRepositoryImpl,
    private val imageDataSource: ImageDataSource,
    private val moshi: Moshi,
) : MemberRepository {
    override suspend fun getMyProfile(): Result<MyProfileContent> {
        val result = runCatching {
            memberDataSource.getMyProfile()
        }
        return if (result.isSuccess) {
            Logger.t("MainTest").i("${result.getOrThrow()}")
            Result.success(result.getOrThrow())
        } else {
            Logger.t("MainTest").i("${result.exception().message}")
            Result.failure(result.exception())
        }
    }

    override suspend fun updateProfileImage(profileURI: String): Result<Unit> {

        val bytes = imageRepositoryImpl.getImageBytes(profileURI)
        val requestFile = bytes.toRequestBody("image/jpeg".toMediaType(), 0, bytes.size)
        val fileName = imageDataSource.getImageName()
        val image = MultipartBody.Part.createFormData(
            "image",
            "$fileName.jpeg",
            requestFile,
        )
        val response = memberDataSource.updateProfileImage(image)

        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Logger.t("updateInfo").i("${response.errorBody()}")
            Result.failure(UnKnownException())
        }
    }

    override suspend fun updateProfileNickname(nickname: String): Result<Unit> {
        val response = memberDataSource.updateProfileNickname(NicknameRequestBody(nickname))

        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(handleServerError(response.code()))
        }
    }

    override suspend fun deleteMember(): Result<Unit> {
        val result = kotlin.runCatching {
            memberDataSource.deleteMember()
        }

        return if (result.isSuccess) {
            Result.success(Unit)
        } else {
            Logger.t("updateInfo").i("${result.exception().message}")
            Result.failure(UnKnownException())
        }
    }

    override suspend fun getParticipatingPats(participatingRequestInfo: ParticipatingRequestInfo): Result<List<ParticipatingContent>> {
        val result = runCatching {
            memberDataSource.getParticipating(
                participatingRequestInfo.lastId,
                participatingRequestInfo.size,
                participatingRequestInfo.state
            )
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow().content)
        } else {
            Result.failure(result.exception())
        }
    }

    override suspend fun getOpenPats(openPatRequestInfo: OpenPatRequestInfo): Result<List<ParticipatingContent>> {
        val result = runCatching {
            memberDataSource.getOpenPats(
                openPatRequestInfo.lastId,
                openPatRequestInfo.size,
                openPatRequestInfo.state
            )
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow().content)
        } else {
            Result.failure(result.exception())
        }
    }

    override suspend fun getParticipatingDetailPats(patId: Long): Result<ParticipatingDetailContent> {
        val result = runCatching {
            memberDataSource.getParticipatingDetail(patId)
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow())
        } else {
            Logger.t("MainTest").i("${result.exception().message}")
            Result.failure(result.exception())
        }
    }

    private fun handleServerError(code: Int): Throwable {
        return when (code) {
                 HTTP_NOT_FOUND -> UserNotFoundException()
                 HTTP_CONFLICT -> InvaildRequestException()
                else -> UnKnownException()
            }
    }
}