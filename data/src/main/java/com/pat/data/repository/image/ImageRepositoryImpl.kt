package com.pat.data.repository.image

import android.graphics.Bitmap
import android.graphics.Matrix
import com.pat.data.source.ImageDataSource
import com.pat.domain.repository.image.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dataSource: ImageDataSource,
    ) : ImageRepository {

    override suspend fun getImageBytes(uri: String): ByteArray {
        val bitmap = dataSource.getBitmapByUri(uri)
        val rotatedBitmap = bitmap.getRotatedBitmap(dataSource.getRotate(uri))
        val scaledBitmap = dataSource.getScaledBitmap(rotatedBitmap)
        val bytes = dataSource.getCompressedBytes(scaledBitmap)
        scaledBitmap.recycle()
        return bytes
    }


    fun Bitmap.getRotatedBitmap(degree: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degree) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true) ?: this
    }


}