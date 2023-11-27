package com.pat.data.repository.member

import com.orhanobut.logger.Logger
import com.pat.data.repository.image.ImageRepositoryImpl
import com.pat.data.source.ImageDataSource
import com.pat.data.source.MemberDataSource
import com.pat.data.source.ProofDataSource
import com.pat.data.util.exception
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.proof.ProofPatInfo
import com.pat.domain.model.proof.ProofContent
import com.pat.domain.model.proof.ProofRequestInfo
import com.pat.domain.repository.MemberRepository
import com.pat.domain.repository.ProofRepository
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberDataSource: MemberDataSource,
) : MemberRepository {
    override suspend fun getParticipatingPats(participatingRequestInfo: ParticipatingRequestInfo): Result<List<ParticipatingContent>> {
        val result = runCatching {
            memberDataSource.getParticipating(participatingRequestInfo.lastId, participatingRequestInfo.size, participatingRequestInfo.sort, participatingRequestInfo.state)
        }
        return if (result.isSuccess) {
            Result.success(result.getOrThrow().content)
        } else {
            Logger.t("MainTest").i("${result.exception().message}")
            Result.failure(result.exception())
        }
    }
}