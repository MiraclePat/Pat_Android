package com.pat.domain.repository.image


interface ImageRepository {
    suspend fun getImageBytes(uri: String): ByteArray



    }
