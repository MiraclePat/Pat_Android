package com.pat.presentation.ui.pat

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.CreatePatInfoDetail
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.usecase.image.GetByteArrayByUriUseCase
import com.pat.domain.usecase.pat.DeletePatUseCase
import com.pat.domain.usecase.pat.GetPatDetailUseCase
import com.pat.domain.usecase.pat.UpdatePatUseCase
import com.pat.domain.usecase.place.GetSearchCoordinateUseCase
import com.pat.domain.usecase.place.GetSearchPlaceUseCase
import com.pat.presentation.model.PatBitmap
import com.pat.presentation.util.image.byteArrayToBitmap
import com.pat.presentation.util.image.getBitmapByExistedUri
import com.pat.presentation.util.image.getCompressedBytes
import com.pat.presentation.util.image.getCompressedExistedBytes
import com.pat.presentation.util.image.getRotatedBitmap
import com.pat.presentation.util.image.getScaledBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PatUpdateUiState(
    val content: PatDetailContent? = null
)

data class UpdateBytes(
    var repBytes: ByteArray,
    var bodyBytes: MutableList<ByteArray>,
    var incorrectBytes: ByteArray,
    var correctBytes: ByteArray,
)

@HiltViewModel
class PatUpdateViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getPatDetailUseCase: GetPatDetailUseCase,
    private val deletePatUseCase: DeletePatUseCase,
    private val updatePatUseCase: UpdatePatUseCase,
    private val getByteArrayByUriUseCase: GetByteArrayByUriUseCase,
    private val getSearchPlaceUseCase: GetSearchPlaceUseCase,
    private val getSearchCoordinateUseCase: GetSearchCoordinateUseCase,
) : ViewModel() {
    private var patId: Long = -1
    private val _uiState = MutableStateFlow(PatUpdateUiState())
    val uiState: StateFlow<PatUpdateUiState> = _uiState.asStateFlow()

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

    private val _bitmapList = MutableStateFlow<UpdateBytes?>(null)
    val bitmapList = _bitmapList.asStateFlow()

    private var selectPlace: String? = null
    private var selectPlaceCoordinate: LatLng? = null


    private val storedBytes: UpdateBytes = UpdateBytes(
        ByteArray(0), mutableListOf(),
        ByteArray(0), ByteArray(0)
    )

    fun getPatDetail(getPatId: Long) {
        viewModelScope.launch {
            patId = getPatId
            val result = getPatDetailUseCase(patId)
            if (result.isSuccess) {
                val content = result.getOrThrow()
                totalBody.clear()
                //썸네일
                beforeBitmapByExistedUri(content.repImg, "REP")
                //옳은 사진
                beforeBitmapByExistedUri(content.correctImg, "CORRECT")
                //틀린 사진
                beforeBitmapByExistedUri(content.incorrectImg, "INCORRECT")
                //팟상세 정보 사진
                if (_bodyBitmap.value.isEmpty()) {
                    content.bodyImg.forEach { uri ->
                        beforeBitmapByExistedUri(uri, "BODY")
                    }
                }
                if (content.location != "") {
                    onSearch(content.location)
                }
                _uiState.emit(PatUpdateUiState(content = content))
            } else {
                Logger.t("MainTest").i("detail viewmodel ${_uiState.value}")
            }
        }
    }


    fun deletePat(patId: Long) {
        viewModelScope.launch {
            val result = deletePatUseCase(patId)
            if (result.isSuccess) {
                result.getOrThrow()
            } else {
                Log.e("custom", "fail")
            }
        }
    }

    //사진 촬영시
    fun onTakePhoto(
        image: ImageProxy,
        bitmapType: String,
        updateState: String?,
        originalIdx: String?
    ) {
        viewModelScope.launch {

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
    }

    //서버에서 들어온 이미지 작업처리
    private fun beforeBitmapByExistedUri(uri: String, bitmapType: String) {
        viewModelScope.launch {
            val newBitmap = getBitmapByExistedUri(uri)
            val bytes = getCompressedExistedBytes(newBitmap)
            when (bitmapType) {
                PatBitmap.REP.toString() -> {
                    if (_repBitmap.value == null) {
                        _repBitmap.value = newBitmap
                        storedBytes.repBytes = bytes
                    }
                }

                PatBitmap.CORRECT.toString() -> {
                    if (_correctBitmap.value == null) {
                        _correctBitmap.value = newBitmap
                        storedBytes.correctBytes = bytes
                    }
                }

                PatBitmap.INCORRECT.toString() -> {
                    if (_incorrectBitmap.value == null) {
                        _incorrectBitmap.value = newBitmap
                        storedBytes.incorrectBytes = bytes
                    }
                }

                PatBitmap.BODY.toString() -> {
                    if (newBitmap != null) {
                        totalBody.add(newBitmap)
                    }
                    _bodyBitmap.value = totalBody
                    storedBytes.bodyBytes.add(bytes)
                }
            }
        }
    }


    //갤러리 선택시
    fun getBitmapByUri(
        uri: String?,
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


    fun updatePat(
        patName: String,
        patDetail: String,
        maxPerson: Int,
        category: String,
        startTime: String,
        endTime: String,
        startDate: String,
        endDate: String,
        proofDetail: String,
        days: List<String>,
        realtime: Boolean,
    ) {
        viewModelScope.launch {
            val detail = CreatePatInfoDetail(
                patName,
                patDetail,
                maxPerson,
                selectPlaceCoordinate?.latitude ?: 0.0,
                selectPlaceCoordinate?.longitude ?: 0.0,
                selectPlace ?: "",
                category,
                startTime,
                endTime,
                startDate,
                endDate,
                proofDetail,
                listOf("월요일", "화요일"),
                realtime
            )
            Logger.t("updateInfo").i("${patId}, ${detail}")

            val result = updatePatUseCase(
                patId,
                CreatePatInfo(
                    storedBytes.repBytes,
                    storedBytes.correctBytes,
                    storedBytes.incorrectBytes,
                    storedBytes.bodyBytes.toList(),
                    detail
                )
            )
            if (result.isSuccess) {
                Logger.t("patdetail").i("성공")
            } else {
                Logger.t("patdetail").i("${result.exceptionOrNull()}")
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
            } else {
                //TODO 에러 처리
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
            } else {
                //TODO 에러 처리 해당주소의 좌표를 찾을 수 없습니다 에러처리
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