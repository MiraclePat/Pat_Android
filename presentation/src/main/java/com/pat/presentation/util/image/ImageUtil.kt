package com.pat.presentation.util.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import androidx.camera.core.ImageProxy
import com.pat.presentation.util.Constants.DEFAULT_QUALITY
import com.pat.presentation.util.Constants.DEFAULT_RESIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL

suspend fun getBitmapByExistedUri(uri: String): Bitmap? =
    withContext(Dispatchers.IO) {
        try {
            val url = URL(uri)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val inputStream = connection.inputStream
            return@withContext BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }

suspend fun getCompressedExistedBytes(bitmap: Bitmap?): ByteArray =
    withContext(Dispatchers.IO) {
        ByteArrayOutputStream().use { outputStream ->
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.toByteArray()
        }
    }


fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun getRotatedBitmap(image: ImageProxy): Bitmap {
    val matrix = Matrix().apply { postRotate(image.imageInfo.rotationDegrees.toFloat()) }
    return Bitmap.createBitmap(image.toBitmap(), 0, 0, image.width, image.height, matrix, true)
}

fun getScaledBitmap(bitmap: Bitmap): Bitmap {
        val (width, height) = getScaledWidthAndHeight(bitmap.width, bitmap.height)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }

fun getCompressedBytes(bitmap: Bitmap): ByteArray =
        ByteArrayOutputStream().use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, DEFAULT_QUALITY, outputStream)
        return outputStream.toByteArray() }


private fun getScaledWidthAndHeight(width: Int, height: Int) =
    when {
        width in DEFAULT_RESIZE until height -> DEFAULT_RESIZE to (DEFAULT_RESIZE / width.toFloat() * height).toInt()
        height in DEFAULT_RESIZE until width -> (DEFAULT_RESIZE / height.toFloat() * height).toInt() to DEFAULT_RESIZE
        else -> width to height
    }