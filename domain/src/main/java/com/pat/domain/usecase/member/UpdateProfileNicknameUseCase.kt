package com.pat.domain.usecase.member

import com.pat.domain.model.member.UpdateProfileInfo
import com.pat.domain.repository.MemberRepository
import javax.inject.Inject

class UpdateProfileNicknameUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
) {
    suspend operator fun invoke(
        nickname: String
    ): Result<Unit> =
        memberRepository.updateProfileNickname(nickname)
}
