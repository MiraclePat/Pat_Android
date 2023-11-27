package com.pat.presentation.ui.pat

import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pat.domain.model.proof.ProofPatInfo
import com.pat.domain.usecase.image.GetByteArrayByUriUseCase
import com.pat.domain.usecase.pat.ProofPatUseCase
import com.pat.presentation.util.image.byteArrayToBitmap
import com.pat.presentation.util.image.getCompressedBytes
import com.pat.presentation.util.image.getRotatedBitmap
import com.pat.presentation.util.image.getScaledBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//data class PattingUiState(
//    val content: List<HomePatContent>? = null
//)

@HiltViewModel
class PattingViewModel @Inject constructor(
    private val getByteArrayByUriUseCase: GetByteArrayByUriUseCase,
    private val proofPatUseCase: ProofPatUseCase,
    ) : ViewModel() {

    private val _bottomSheetState = MutableStateFlow<Boolean>(false)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private val _proofBitmap = MutableStateFlow<Bitmap?>(null)
    val proofBitmap = _proofBitmap.asStateFlow()

    private var proofImageBytes: ByteArray = ByteArray(0)

    fun onTakePhoto(image: ImageProxy) {
        val rotatedBitmap = getRotatedBitmap(image)
        val scaledBitmap = getScaledBitmap(rotatedBitmap)
        val bytes = getCompressedBytes(scaledBitmap)
        val newBitmap = byteArrayToBitmap(bytes)
        _proofBitmap.value = newBitmap
        _bottomSheetState.value = true
        proofImageBytes = bytes
    }

    fun getBitmapByUri(uri: Uri?) {
        viewModelScope.launch {
            val bytes = getByteArrayByUriUseCase(uri.toString())
            val newBitmap = byteArrayToBitmap(bytes)
            _proofBitmap.value = newBitmap
            proofImageBytes = bytes
        }
    }

    fun clearBitmap(){
        _proofBitmap.value = null
        _bottomSheetState.value = false
    }

    fun proofPat() {
        viewModelScope.launch {
                val result = proofPatUseCase(1L, ProofPatInfo(proofImageBytes))
                if (result.isSuccess) {
                    //TODO Request to Sever?
                } else {
                    //TODO 에러 처리
                }
            }
        }

}



