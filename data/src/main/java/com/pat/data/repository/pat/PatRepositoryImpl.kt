package com.pat.data.repository.pat

import com.orhanobut.logger.Logger
import com.pat.data.repository.image.ImageRepositoryImpl
import com.pat.data.source.ImageDataSource
import com.pat.data.source.PatDataSource
import com.pat.data.util.exception
import com.pat.domain.model.exception.BadRequestException
import com.pat.domain.model.exception.ForbiddenException
import com.pat.domain.model.exception.NotFoundException
import com.pat.domain.model.exception.UnKnownException
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.CreatePatInfoDetail
import com.pat.domain.model.pat.HomeBannerContent
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.pat.MapPatContent
import com.pat.domain.model.pat.MapPatRequestInfo
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.repository.PatRepository
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.http.HTTP_BAD_REQUEST
import okhttp3.internal.http.HTTP_FORBIDDEN
import okhttp3.internal.http.HTTP_NOT_FOUND
import retrofit2.HttpException
import javax.inject.Inject

class PatRepositoryImpl @Inject constructor(
    private val patDataSource: PatDataSource,
    private val imageRepositoryImpl: ImageRepositoryImpl,
    private val imageDataSource: ImageDataSource,
    private val moshi: Moshi,

    ) : PatRepository {
    override suspend fun getHomeBanner(): Result<HomeBannerContent> {
        val result = runCatching {
            patDataSource.getHomeBanner()
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow())
        } else {
            Logger.t("MainTest").i("${result.exception().message}")

            Result.failure(result.exception())
        }
    }

    override suspend fun getHomePats(homePatRequestInfo: HomePatRequestInfo): Result<List<HomePatContent>> {
        //TODO hasNext
        val result = runCatching {
            patDataSource.getHomePats(
                homePatRequestInfo.lastId, homePatRequestInfo.size,
                homePatRequestInfo.sort, homePatRequestInfo.query, homePatRequestInfo.category,
                homePatRequestInfo.showFull, homePatRequestInfo.state
            )
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow().content)
        } else {
            Logger.t("MainTest").i("${result.exception().message}")
            Result.failure(result.exception())
        }
    }

    override suspend fun getMapPats(mapPatRequestInfo: MapPatRequestInfo): Result<List<MapPatContent>> {
        val result = runCatching {
            patDataSource.getMapPats(
                mapPatRequestInfo.size,
                mapPatRequestInfo.query, mapPatRequestInfo.category,
                mapPatRequestInfo.state, mapPatRequestInfo.showFull,
                mapPatRequestInfo.leftLongitude, mapPatRequestInfo.rightLongitude,
                mapPatRequestInfo.bottomLatitude, mapPatRequestInfo.topLatitude
            )
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow().content)
        } else {
            Logger.t("naverMap").i("${result.exception().message}")
            Result.failure(result.exception())
        }
    }

    private suspend fun getMultipartImage(bytes: ByteArray, partName: String): MultipartBody.Part {
        val requestFile = bytes.toRequestBody("image/jpeg".toMediaType(), 0, bytes.size)
        val fileName = imageDataSource.getImageName()
        return MultipartBody.Part.createFormData(
            partName,
            "$fileName.jpeg",
            requestFile,
        )
    }

    override suspend fun createPat(createPatInfo: CreatePatInfo): Result<Unit> {
        val result = runCatching {
            val repImg = getMultipartImage(createPatInfo.repImg, "repImg")
            val correctImg = getMultipartImage(createPatInfo.correctImg, "correctImg")
            val incorrectImg = getMultipartImage(createPatInfo.incorrectImg, "incorrectImg")
            val bodyImg = createPatInfo.bodyImg.map {
                getMultipartImage(it, "bodyImg")
            }

            val adapter = moshi.adapter(CreatePatInfoDetail::class.java)
            val patInfoJson = adapter.toJson(createPatInfo.pat)
            val patRequestBody = patInfoJson.toRequestBody("application/json".toMediaTypeOrNull())
            val patPart = MultipartBody.Part.createFormData("pat", "pat", patRequestBody)

            patDataSource.createPat(repImg, correctImg, incorrectImg, bodyImg, patPart)
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow())
        } else {
            Logger.t("errorCode").i("${result.exceptionOrNull()}")

            Result.failure(handleRegisterAndGetError(result.exception()))
        }
    }

    override suspend fun getPatDetail(patId: Long): Result<PatDetailContent> {
        val result = runCatching {
            patDataSource.getPatDetail(patId)
        }
        return if (result.isSuccess) {
            Logger.t("MainTest").i("${result.getOrThrow()}")

            Result.success(result.getOrThrow())
        } else {
            Logger.t("MainTest").i("${result.exception().message}")

            Result.failure(result.exception())
        }
    }

    override suspend fun participatePat(patId: Long): Result<Unit> {
        val response = patDataSource.participatePat(patId)
        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Logger.t("MainTest").i("${response.errorBody()}")
            Result.failure(UnKnownException())
        }
    }

    override suspend fun updatePat(patId: Long, createPatInfo: CreatePatInfo): Result<Unit> {
        val repImg = getMultipartImage(createPatInfo.repImg, "repImg")
        val correctImg = getMultipartImage(createPatInfo.correctImg, "correctImg")
        val incorrectImg = getMultipartImage(createPatInfo.incorrectImg, "incorrectImg")
        val bodyImg = createPatInfo.bodyImg.map {
            getMultipartImage(it, "bodyImg")
        }

        val adapter = moshi.adapter(CreatePatInfoDetail::class.java)
        val patInfoJson = adapter.toJson(createPatInfo.pat)
        val patRequestBody = patInfoJson.toRequestBody("application/json".toMediaTypeOrNull())
        val patPart = MultipartBody.Part.createFormData("pat", "pat", patRequestBody)

        val response =
            patDataSource.updatePat(patId, repImg, correctImg, incorrectImg, bodyImg, patPart)

        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Logger.t("updateInfo").i("${response.errorBody()}")

            Result.failure(UnKnownException())
        }
    }

    override suspend fun deletePat(patId: Long): Result<Unit> {
        val response = patDataSource.deletePat(patId)
        return if (response.isSuccessful) {
            Logger.t("MainTest").i("${response}, ${patId}")
            Result.success(Unit)
        } else {
            Logger.t("MainTest").i("${response}, ${patId}")
            Result.failure(UnKnownException())
        }
    }

    override suspend fun withdrawPat(patId: Long): Result<Unit> {
        val response = patDataSource.withdrawPat(patId)
        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(UnKnownException())
        }
    }

    private fun handleRegisterAndGetError(t: Throwable): Throwable {
        return if (t is HttpException) {
            val message = t.response()?.errorBody()?.string().toString()
            when (t.code()) {
                HTTP_FORBIDDEN -> {
                    Logger.t("code").i(message)
                    ForbiddenException()
                }

                HTTP_BAD_REQUEST -> {
                    Logger.t("code").i(message)
                    BadRequestException()
                }

                HTTP_NOT_FOUND -> {
                    Logger.t("code").i(message)
                    NotFoundException()
                }

                else -> UnKnownException()
            }
        } else {
            Logger.t("MainTest").i("실패 ${t.message}")
            t
        }
    }
}
