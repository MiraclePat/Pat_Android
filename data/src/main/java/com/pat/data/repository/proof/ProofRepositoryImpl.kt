package com.pat.data.repository.proof

import com.orhanobut.logger.Logger
import com.pat.data.repository.image.ImageRepositoryImpl
import com.pat.data.source.ImageDataSource
import com.pat.data.source.ProofDataSource
import com.pat.data.util.exception
import com.pat.domain.model.proof.ProofPatInfo
import com.pat.domain.model.proof.ProofContent
import com.pat.domain.model.proof.ProofRequestInfo
import com.pat.domain.repository.ProofRepository
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ProofRepositoryImpl @Inject constructor(
    private val proofDataSource: ProofDataSource,
    private val imageRepositoryImpl: ImageRepositoryImpl,
    private val imageDataSource: ImageDataSource,
    private val moshi: Moshi,
) : ProofRepository {
    override suspend fun proofPat(patId:Long, proofPatInfo: ProofPatInfo): Result<Unit> {
        val result = runCatching {
            val proofImg = getMultipartImage(proofPatInfo.proofImg, "proofImg")
            proofDataSource.proofPat(patId, proofImg)
        }
        return if (result.isSuccess) {
            Logger.t("MainTest").i("${result.getOrThrow()}")
            Result.success(result.getOrThrow())
        } else {
            Logger.t("MainTest").i("${result.exception().message}")
            Result.failure(result.exception())
        }
    }

    override suspend fun getMyProofs(
        patId: Long,
        proofRequestInfo: ProofRequestInfo
    ): Result<List<ProofContent>> {
        val result = kotlin.runCatching {
            proofDataSource.getMyProof(patId, proofRequestInfo.lastId, proofRequestInfo.size)
        }
        return if (result.isSuccess) {
            Logger.t("MainTest").i("${result.getOrThrow()}")
            Result.success(result.getOrThrow().content)
        } else {
            Logger.t("MainTest").i("${result.exception().message}")
            Result.failure(result.exception())
        }
    }

    override suspend fun getSomeoneProofs(
        patId: Long,
        proofRequestInfo: ProofRequestInfo
    ): Result<List<ProofContent>> {
        val result = kotlin.runCatching {
            proofDataSource.getSomeoneProof(patId, proofRequestInfo.lastId, proofRequestInfo.size)
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow().content)
        } else {
            Logger.t("MainTest").i("${result.exception().message}")
            Result.failure(result.exception())
        }
    }

    private fun getMultipartImage(bytes: ByteArray, partName:String): MultipartBody.Part {
        val requestFile = bytes.toRequestBody("image/jpeg".toMediaType(), 0, bytes.size)
        val fileName = imageDataSource.getImageName()
        return MultipartBody.Part.createFormData(
            partName,
            "${fileName}.jpeg",   //TODO 수정
            requestFile,
        )
    }
}