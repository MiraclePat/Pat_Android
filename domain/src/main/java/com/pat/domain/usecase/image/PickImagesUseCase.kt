package com.pat.domain.usecase.image

import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.repository.PatRepository
import com.weit.domain.repository.image.GalleryRepository
import javax.inject.Inject

class PickImagesUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository,
) {
    suspend operator fun invoke(
    ): List<String> =
        galleryRepository.pickImages()
}
