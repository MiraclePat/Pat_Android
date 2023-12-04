package com.pat.data.repository.auth

import com.orhanobut.logger.Logger
import com.pat.data.repository.image.ImageRepositoryImpl
import com.pat.data.source.ImageDataSource
import com.pat.data.source.MemberDataSource
import com.pat.data.util.exception
import com.pat.domain.model.exception.UnKnownException
import com.pat.domain.model.member.MyProfileContent
import com.pat.domain.model.member.OpenPatRequestInfo
import com.pat.domain.model.member.ParticipatingContent
import com.pat.domain.model.member.ParticipatingDetailContent
import com.pat.domain.model.member.ParticipatingRequestInfo
import com.pat.domain.model.member.UpdateProfileInfo
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.CreatePatInfoDetail
import com.pat.domain.repository.AuthRepository
import com.pat.domain.repository.MemberRepository
import com.pat.domain.repository.ProofRepository
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
) : AuthRepository {

}