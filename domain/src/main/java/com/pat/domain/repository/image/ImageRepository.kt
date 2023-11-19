package com.weit.domain.repository.image


interface ImageRepository {
    suspend fun getImageBytes(uri: String): ByteArray
}
