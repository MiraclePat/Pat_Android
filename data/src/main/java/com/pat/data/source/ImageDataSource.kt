package com.pat.data.source

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ImageDataSource @Inject constructor(
    private val contentResolver: ContentResolver,
    ) {
    suspend fun getCompressedBytes(bitmap: Bitmap): ByteArray =
        withContext(Dispatchers.IO) {
            ByteArrayOutputStream().use { outputStream ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, DEFAULT_QUALITY, outputStream)
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, DEFAULT_QUALITY, outputStream)
                }
                outputStream.toByteArray()
            }
        }

    suspend fun getBitmapByUri(uri: String): Bitmap =
        withContext(Dispatchers.IO) {
            contentResolver.openInputStream(Uri.parse(uri)).use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }

    suspend fun getScaledBitmap(bitmap: Bitmap): Bitmap =
        withContext(Dispatchers.IO) {
            val (width, height) = getScaledWidthAndHeight(bitmap.width, bitmap.height)
            Bitmap.createScaledBitmap(bitmap, width, height, false)
        }

    private fun getScaledWidthAndHeight(width: Int, height: Int) =
        when {
            width in DEFAULT_RESIZE until height -> DEFAULT_RESIZE to (DEFAULT_RESIZE / width.toFloat() * height).toInt()
            height in DEFAULT_RESIZE until width -> (DEFAULT_RESIZE / height.toFloat() * height).toInt() to DEFAULT_RESIZE
            else -> width to height
        }

    fun getImageName(): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val randomString = (1..8)
            .map { allowedChars.random() }
            .joinToString("")
        return "photo_${timestamp}_${randomString}"
}

    suspend fun getRotate(uri: String): Float {
        var orientation : Float = 0f
        contentResolver.openInputStream(Uri.parse(uri))?.use { inputStream ->
            val exif = ExifInterface(inputStream)
            orientation = when (exif.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 0)) {
                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                androidx.exifinterface.media.ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }
        }
        return orientation
    }

    companion object {
        private const val DEFAULT_QUALITY = 90
        private const val DEFAULT_RESIZE = 1024
    }
}
