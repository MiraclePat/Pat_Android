package com.pat.domain.usecase.image

import com.pat.domain.repository.image.ImageRepository
import javax.inject.Inject

class GetByteArrayByUriUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(
        uri: String,
    ): ByteArray =
        imageRepository.getImageBytes(uri)
}
