package com.pat.presentation.ui.post

import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.orhanobut.logger.Logger
import com.pat.domain.model.exception.ForbiddenException
import com.pat.domain.model.exception.NotFoundException
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.CreatePatInfoDetail
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.usecase.image.GetByteArrayByUriUseCase
import com.pat.domain.usecase.pat.CreatePatUseCase
import com.pat.domain.usecase.place.GetSearchCoordinateUseCase
import com.pat.domain.usecase.place.GetSearchPlaceUseCase
import com.pat.presentation.model.PatBitmap
import com.pat.presentation.util.image.byteArrayToBitmap
import com.pat.presentation.util.image.getCompressedBytes
import com.pat.presentation.util.image.getRotatedBitmap
import com.pat.presentation.util.image.getScaledBitmap
import com.pat.presentation.util.resultException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostBytes(
    var repBytes: ByteArray,
    var bodyBytes: MutableList<ByteArray>,
    var incorrectBytes: ByteArray,
    var correctBytes: ByteArray,
)

sealed class PostEvent {
    object PostSuccess : PostEvent()
    object PostFailed : PostEvent()
    object SearchPlaceSuccess : PostEvent()
    object SearchPlaceFailed : PostEvent()
    object SearchCoordinateSuccess : PostEvent()
    object SearchCoordinateFailed : PostEvent()
}

@HiltViewModel
class PostViewModel @Inject constructor(
    private val createPatUseCase: CreatePatUseCase,
    private val getByteArrayByUriUseCase: GetByteArrayByUriUseCase,
    private val getSearchPlaceUseCase: GetSearchPlaceUseCase,
    private val getSearchCoordinateUseCase: GetSearchCoordinateUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<PostEvent>()
    val event = _event.asSharedFlow()

    private var searchJob: Job = Job().apply {
        complete()
    }

    private val _searchPlaceResult = MutableStateFlow<List<PlaceDetailInfo>>(emptyList())
    val searchPlaceResult = _searchPlaceResult.asStateFlow()


    private val totalBody = mutableListOf<Bitmap>()

    private val _bodyBitmap = MutableStateFlow<List<Bitmap?>>(emptyList())
    val bodyBitmap = _bodyBitmap.asStateFlow()

    private val _repBitmap = MutableStateFlow<Bitmap?>(null)
    val repBitmap = _repBitmap.asStateFlow()

    private val _correctBitmap = MutableStateFlow<Bitmap?>(null)
    val correctBitmap = _correctBitmap.asStateFlow()

    private val _incorrectBitmap = MutableStateFlow<Bitmap?>(null)
    val incorrectBitmap = _incorrectBitmap.asStateFlow()

    private val _bitmapList = MutableStateFlow<PostBytes?>(null)
    val bitmapList = _bitmapList.asStateFlow()

    private var selectPlace: String? = null
    private var selectPlaceCoordinate: LatLng? = null


    private val storedBytes: PostBytes = PostBytes(
        ByteArray(0), mutableListOf(),
        ByteArray(0), ByteArray(0)
    )


    fun onTakePhoto(
        image: ImageProxy,
        bitmapType: String,
        updateState: String?,
        originalIdx: String?
    ) {
        val rotatedBitmap = getRotatedBitmap(image)
        val scaledBitmap = getScaledBitmap(rotatedBitmap)
        val bytes = getCompressedBytes(scaledBitmap)
        val newBitmap = byteArrayToBitmap(bytes)

        when (bitmapType) {
            PatBitmap.REP.toString() -> {
                _repBitmap.value = newBitmap
                storedBytes.repBytes = bytes
            }

            PatBitmap.CORRECT.toString() -> {
                _correctBitmap.value = newBitmap
                storedBytes.correctBytes = bytes
            }

            PatBitmap.INCORRECT.toString() -> {
                _incorrectBitmap.value = newBitmap
                storedBytes.incorrectBytes = bytes
            }

            PatBitmap.BODY.toString() -> {
                if (updateState == "true" && totalBody.isNotEmpty() && originalIdx != null) {
                    val idx = originalIdx.toInt()
                    totalBody[idx] = newBitmap
                    storedBytes.bodyBytes[idx] = bytes
                    _bodyBitmap.value = totalBody
                } else {
                    totalBody.add(newBitmap)
                    _bodyBitmap.value = totalBody
                    storedBytes.bodyBytes.add(bytes)
                }
            }
        }
    }


    fun getBitmapByUri(
        uri: Uri?,
        bitmapType: String,
        updateState: String? = null,
        originalIdx: Int? = null
    ) {
        viewModelScope.launch {
            val bytes = getByteArrayByUriUseCase(uri.toString())
            val newBitmap = byteArrayToBitmap(bytes)
            when (bitmapType) {
                PatBitmap.REP.toString() -> {
                    _repBitmap.value = newBitmap
                    storedBytes.repBytes = bytes
                }

                PatBitmap.CORRECT.toString() -> {
                    _correctBitmap.value = newBitmap
                    storedBytes.correctBytes = bytes
                }

                PatBitmap.INCORRECT.toString() -> {
                    _incorrectBitmap.value = newBitmap
                    storedBytes.incorrectBytes = bytes
                }

                PatBitmap.BODY.toString() -> {
                    if (updateState == "true" && totalBody.isNotEmpty() && originalIdx != null) {
                        totalBody[originalIdx] = newBitmap
                        storedBytes.bodyBytes[originalIdx] = bytes
                        _bodyBitmap.value = totalBody
                    } else {
                        totalBody.add(newBitmap)
                        _bodyBitmap.value = totalBody
                        storedBytes.bodyBytes.add(bytes)
                    }
                }
            }
        }
    }


    fun post(
        patName: String, patDetail: String, maxPerson: Int, category: String,
        startTime: String, endTime: String, startDate: String, endDate: String,
        proofDetail: String, days: List<String>, realtime: Boolean,
    ) {
        viewModelScope.launch {
            val detail = CreatePatInfoDetail(
                patName, patDetail, maxPerson,
                selectPlaceCoordinate?.latitude ?: 0.0, selectPlaceCoordinate?.longitude ?: 0.0,
                selectPlace ?: "", category, startTime, endTime,
                startDate, endDate, proofDetail, days, realtime
            )
            val result = createPatUseCase(
                CreatePatInfo(
                    storedBytes.repBytes, storedBytes.correctBytes,
                    storedBytes.incorrectBytes, storedBytes.bodyBytes.toList(),
                    detail
                )
            )
            if (result.isSuccess) {
                _event.emit(PostEvent.PostSuccess)
            } else {
                _event.emit(PostEvent.PostFailed)
                val error = result.exceptionOrNull()
                resultException(error)
            }
        }
    }


    fun onSearch(query: String) {
        searchJob.cancel()
        _searchPlaceResult.value = emptyList()
        searchPlace(query)
    }

    private fun searchPlace(query: String) {
        searchJob = viewModelScope.launch {
            val result = getSearchPlaceUseCase(
                PlaceSearchRequestInfo(
                    query, MAX_PLACE_SIZE
                )
            )
            if (result.isSuccess) {
                val places = result.getOrThrow()
                places.forEach { place ->
                    place.title = place.title.toString().replace("<b>", "").replace("</b>", "")
                }
                _searchPlaceResult.emit(places)
                _event.emit(PostEvent.SearchPlaceSuccess)
            } else {
                _event.emit(PostEvent.SearchPlaceFailed)
                val error = result.exceptionOrNull()
                resultException(error)
            }
        }
    }

    private fun searchCoordinate(place: PlaceDetailInfo) {
        viewModelScope.launch {
            val result = getSearchCoordinateUseCase(
                place.address.toString()
            )
            if (result.isSuccess) {
                val coordinate = result.getOrThrow()
                selectPlaceCoordinate = LatLng(coordinate.lat, coordinate.long)
                _event.emit(PostEvent.SearchCoordinateSuccess)
            } else {
                //TODO 에러 처리 해당주소의 좌표를 찾을 수 없습니다 에러처리
                _event.emit(PostEvent.SearchCoordinateFailed)
                val error = result.exceptionOrNull()
                resultException(error)
            }
        }
    }


    fun selectPlace(place: PlaceDetailInfo? = null) {
        if (place != null) {
            selectPlace = place.title
            searchCoordinate(place)
        } else {
            selectPlace = ""
        }

    }

    fun clearImageData() {
        _repBitmap.value = null
        storedBytes.repBytes = ByteArray(0)
        _correctBitmap.value = null
        storedBytes.correctBytes = ByteArray(0)
        _incorrectBitmap.value = null
        storedBytes.incorrectBytes = ByteArray(0)
        totalBody.clear()
        _bodyBitmap.value = emptyList()
        storedBytes.bodyBytes = mutableListOf()
    }

    companion object {
        const val MAX_PLACE_SIZE = 5
    }

}