package com.pat.data.repository.member

import com.orhanobut.logger.Logger
import com.pat.data.source.MemberDataSource
import com.pat.data.util.exception
import com.pat.domain.model.member.OpenPatRequestInfo
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingDetailContent
import com.pat.domain.model.member.ParticipatingRequestInfo
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
                openPatRequestInfo.sort
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
            Logger.t("MainTest").i("${result.getOrThrow()}")
            Result.success(result.getOrThrow())
        } else {
            Logger.t("MainTest").i("${result.exception().message}")
            Result.failure(result.exception())
        }
    }
}