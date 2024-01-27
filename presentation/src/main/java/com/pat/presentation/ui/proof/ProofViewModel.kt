package com.pat.presentation.ui.proof

import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.pat.domain.model.member.ParticipatingDetailContent
import com.pat.domain.model.proof.ProofPatInfo
import com.pat.domain.model.proof.ProofRequestInfo
import com.pat.domain.usecase.image.GetByteArrayByUriUseCase
import com.pat.domain.usecase.member.GetParticipatingDetailUseCase
import com.pat.domain.usecase.pat.WithdrawPatUseCase
import com.pat.domain.usecase.proof.GetMyProofUseCase
import com.pat.domain.usecase.proof.GetSomeoneProofUseCase
import com.pat.domain.usecase.proof.ProofPatUseCase
import com.pat.presentation.ui.proof.paging.MyProofPaging
import com.pat.presentation.ui.proof.paging.SomeoneProofPaging
import com.pat.presentation.util.image.byteArrayToBitmap
import com.pat.presentation.util.image.getCompressedBytes
import com.pat.presentation.util.image.getRotatedBitmap
import com.pat.presentation.util.image.getScaledBitmap
import com.pat.presentation.util.resultException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ProofEvent {
    object GetPatInfoSuccess : ProofEvent()
    object GetPatInfoFailed : ProofEvent()
    object ProofSuccess : ProofEvent()
    object ProofFailed : ProofEvent()
    object WithdrawSuccess : ProofEvent()
    object WithdrawFailed : ProofEvent()
}

data class ParticipatingUiState(
    val content: ParticipatingDetailContent? = null
)

@HiltViewModel
class ProofViewModel @Inject constructor(
    private val proofPatUseCase: ProofPatUseCase,
    private val getMyProofUseCase: GetMyProofUseCase,
    private val getSomeoneProofUseCase: GetSomeoneProofUseCase,
    private val getParticipatingDetailUseCase: GetParticipatingDetailUseCase,
    private val getByteArrayByUriUseCase: GetByteArrayByUriUseCase,
    private val withdrawPatUseCase: WithdrawPatUseCase,
) : ViewModel() {
    private var patId: Long = -1
    private val size = 10

    private val _bottomSheetState = MutableStateFlow<Boolean>(false)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private val _proofBitmap = MutableStateFlow<Bitmap?>(null)
    val proofBitmap = _proofBitmap.asStateFlow()

    var proofImageBytes: ByteArray = ByteArray(0)

    private val _uiState = MutableStateFlow(ParticipatingUiState())
    val uiState: StateFlow<ParticipatingUiState> = _uiState.asStateFlow()

    private val pagingId = MutableStateFlow(-1L)

    private val _event = MutableSharedFlow<ProofEvent>()
    val event = _event.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    var myProof = pagingId.flatMapLatest { id ->
        Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                MyProofPaging(
                    id,
                    getMyProofUseCase,
                    ProofRequestInfo(
                        size = size
                    )
                )

            }
        ).flow.cachedIn(viewModelScope)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    val someoneProof = pagingId.flatMapLatest { id ->
        Pager(
            config = PagingConfig(pageSize = size),
            pagingSourceFactory = {
                SomeoneProofPaging(
                    id,
                    getSomeoneProofUseCase,
                    ProofRequestInfo(
                        size = size
                    )
                )

            }
        ).flow.cachedIn(viewModelScope)
    }


    fun getParticipatingDetail(getPatId: Long) {
        viewModelScope.launch {
            patId = getPatId
            pagingId.emit(patId)
            val result = getParticipatingDetailUseCase(patId)
            if (result.isSuccess) {
                val content = result.getOrThrow()
                _uiState.emit(ParticipatingUiState(content = content))
                _event.emit(ProofEvent.GetPatInfoSuccess)
            } else {
                _event.emit(ProofEvent.GetPatInfoFailed)
            }
        }
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun proofPat(patId: Long) {
        viewModelScope.launch {
            val result = proofPatUseCase(patId, ProofPatInfo(proofImageBytes))
            if (result.isSuccess) {
                result.getOrThrow()
                clearBitmap()
                getParticipatingDetail(_uiState.value.content!!.patId)
                _event.emit(ProofEvent.ProofSuccess)
                myProof = pagingId.flatMapLatest { id ->
                    Pager(
                        config = PagingConfig(pageSize = size),
                        pagingSourceFactory = {
                            MyProofPaging(
                                id,
                                getMyProofUseCase,
                                ProofRequestInfo(
                                    size = size
                                )
                            )

                        }
                    ).flow.cachedIn(viewModelScope)
                }
            } else {
                _event.emit(ProofEvent.ProofFailed)
                val error = result.exceptionOrNull()
                resultException(error)
            }
        }
    }

    fun withdrawPat(patId: Long) {
        viewModelScope.launch {
            val result = withdrawPatUseCase(patId)
            if (result.isSuccess) {
                result.getOrThrow()
                _event.emit(ProofEvent.WithdrawSuccess)
            } else {
                _event.emit(ProofEvent.WithdrawFailed)
                val error = result.exceptionOrNull()
                resultException(error)
            }
        }
    }
}