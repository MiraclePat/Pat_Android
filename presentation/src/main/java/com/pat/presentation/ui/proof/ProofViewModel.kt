package com.pat.presentation.ui.proof

import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.pat.domain.model.member.ParticipatingDetailContent
import com.pat.domain.model.proof.ProofPatInfo
import com.pat.domain.model.proof.ProofContent
import com.pat.domain.model.proof.ProofRequestInfo
import com.pat.domain.usecase.image.GetByteArrayByUriUseCase
import com.pat.domain.usecase.member.GetParticipatingDetailUseCase
import com.pat.domain.usecase.proof.GetMyProofUseCase
import com.pat.domain.usecase.proof.GetSomeoneProofUseCase
import com.pat.domain.usecase.proof.ProofPatUseCase
import com.pat.presentation.util.image.byteArrayToBitmap
import com.pat.presentation.util.image.getCompressedBytes
import com.pat.presentation.util.image.getRotatedBitmap
import com.pat.presentation.util.image.getScaledBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ParticipatingUiState(
    val content: ParticipatingDetailContent? = null
)

data class ProofUiState(
    val content: List<ProofContent>? = null
)

@HiltViewModel
class ProofViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val proofPatUseCase: ProofPatUseCase,
    private val getMyProofUseCase: GetMyProofUseCase,
    private val getSomeoneProofUseCase: GetSomeoneProofUseCase,
    private val getParticipatingDetailUseCase: GetParticipatingDetailUseCase,
    private val getByteArrayByUriUseCase: GetByteArrayByUriUseCase,
) : ViewModel() {
    private val patId = savedStateHandle.get<Long?>(
        key = "patId"
    ) ?: -1

    private val _bottomSheetState = MutableStateFlow<Boolean>(false)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private val _proofBitmap = MutableStateFlow<Bitmap?>(null)
    val proofBitmap = _proofBitmap.asStateFlow()

    var proofImageBytes: ByteArray = ByteArray(0)

    private val _uiState = MutableStateFlow(ParticipatingUiState())
    val uiState: StateFlow<ParticipatingUiState> = _uiState.asStateFlow()

    private val _proofs = MutableStateFlow(ProofUiState())
    val proofs: StateFlow<ProofUiState> = _proofs.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getParticipatingDetailUseCase(patId)
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(ParticipatingUiState(content = content))
            } else {
                Logger.t("MainTest").i("${uiState}")
            }
        }
        getMyProof()
    }

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

    fun clearBitmap() {
        _proofBitmap.value = null
        _bottomSheetState.value = false
    }

    fun proofPat(proofImg: ByteArray) {
        viewModelScope.launch {
            val result = proofPatUseCase(patId, ProofPatInfo(proofImg))
            if (result.isSuccess) {
                result.getOrThrow()
            } else {
                Logger.t("MainTest").i("${result}")
            }
        }
    }

    fun getMyProof() {
        viewModelScope.launch {
            val result = getMyProofUseCase(patId, ProofRequestInfo())
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _proofs.emit(ProofUiState(content = content))
            } else {
                Logger.t("MainTest").i("patid: ${patId}")
            }
        }
    }


    fun getSomeoneProof() {
        viewModelScope.launch {
            val result = getSomeoneProofUseCase(patId, ProofRequestInfo())
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _proofs.emit(ProofUiState(content = content))
            } else {
                Logger.t("MainTest").i("${proofs}")
            }
        }
    }
}