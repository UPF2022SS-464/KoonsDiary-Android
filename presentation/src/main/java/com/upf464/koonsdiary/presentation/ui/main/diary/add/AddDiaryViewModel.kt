package com.upf464.koonsdiary.presentation.ui.main.diary.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.usecase.calendar.FetchCalendarFlowUseCase
import com.upf464.koonsdiary.domain.usecase.diary.AddDiaryUseCase
import com.upf464.koonsdiary.domain.usecase.diary.AnalyzeSentimentUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.mapper.toDomain
import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class AddDiaryViewModel @Inject constructor(
    private val addDiaryUseCase: AddDiaryUseCase,
    private val analyzeSentimentUseCase: AnalyzeSentimentUseCase,
    private val fetchCalendarFlowUseCase: FetchCalendarFlowUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var fetchCalendarJob: Job? = null

    private val _availableDateStateFlow = MutableStateFlow<AvailableDateState>(AvailableDateState.Closed)
    val availableDateStateFlow = _availableDateStateFlow.asStateFlow()

    private val _dateFlow = MutableStateFlow(savedStateHandle.get<String>(Constants.PARAM_DATE)?.let {
        LocalDate.parse(it)
    } ?: LocalDate.now())
    val dateFlow = _dateFlow.asStateFlow()

    private val _imageListFlow = MutableStateFlow<List<DiaryImageModel>>(emptyList())
    val imageListFlow = _imageListFlow.asStateFlow()
    val showAddImageFlow = imageListFlow.map { it.size < MAX_IMAGE_LIST_SIZE }

    private val _imageDialogStateFlow = MutableStateFlow<ImageDialogState>(ImageDialogState.Closed)
    val imageDialogStateFlow = _imageDialogStateFlow.asStateFlow()

    private var sentiment: Sentiment? = null

    private val _sentimentStateFlow = MutableStateFlow<SentimentState>(SentimentState.Closed)
    val sentimentStateFlow = _sentimentStateFlow.asStateFlow()

    val contentFlow = MutableStateFlow("")

    private val _eventFlow = MutableSharedFlow<AddDiaryEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    fun selectDate(day: Int) {
        val dateState = _availableDateStateFlow.value
        if (dateState is AvailableDateState.Success) {
            _dateFlow.value = LocalDate.of(dateState.year, dateState.month, day)
        }

        _availableDateStateFlow.value = AvailableDateState.Closed
    }

    fun toggleAvailableDate() {
        if (availableDateStateFlow.value == AvailableDateState.Closed) {
            val date = dateFlow.value
            _availableDateStateFlow.value = AvailableDateState.Loading(date.year, date.monthValue)
            fetchAvailableDate(date.year, date.monthValue)
        } else {
            _availableDateStateFlow.value = AvailableDateState.Closed
        }
    }

    fun nextAvailableDate() {
        val (year, month) = when (val dateState = _availableDateStateFlow.value) {
            AvailableDateState.Closed -> return
            is AvailableDateState.Loading -> dateState.year to dateState.month
            is AvailableDateState.Success -> dateState.year to dateState.month
        }

        if (month == 12) {
            fetchAvailableDate(year + 1, 1)
        } else {
            fetchAvailableDate(year, month + 1)
        }
    }

    fun previousAvailableDate() {
        val (year, month) = when (val dateState = _availableDateStateFlow.value) {
            AvailableDateState.Closed -> return
            is AvailableDateState.Loading -> dateState.year to dateState.month
            is AvailableDateState.Success -> dateState.year to dateState.month
        }

        if (month == 1) {
            fetchAvailableDate(year - 1, 12)
        } else {
            fetchAvailableDate(year, month - 1)
        }
    }

    private fun fetchAvailableDate(year: Int, month: Int) {
        fetchCalendarJob?.cancel()
        fetchCalendarJob = viewModelScope.launch {
            fetchCalendarFlowUseCase(FetchCalendarFlowUseCase.Request(year, month)).collect { result ->
                result.onSuccess { response ->
                    _availableDateStateFlow.value = AvailableDateState.Success(
                        year = year,
                        month = month,
                        dateList = response.sentimentList.map { it == null }
                    )
                }.onFailure {
                    // TODO("오류 처리")
                }
            }
        }
    }

    fun addImage(imagePath: String) {
        _imageListFlow.value = _imageListFlow.value + DiaryImageModel(imagePath)
    }

    fun openImageDialog(index: Int) {
        _imageDialogStateFlow.value = ImageDialogState.Opened(index)
    }

    fun closeImageDialog() {
        _imageDialogStateFlow.value = ImageDialogState.Closed
    }

    fun deleteImage(index: Int) {
        val imageList = imageListFlow.value
        _imageListFlow.value = imageList.subList(0, index) + imageList.subList(index + 1, imageList.size)
        _imageDialogStateFlow.value = ImageDialogState.Closed
    }

    fun startSelectSentiment() {
        _sentimentStateFlow.value = SentimentState.InSelect
    }

    fun selectSentiment(sentiment: Sentiment) {
        _sentimentStateFlow.value = SentimentState.Selected(sentiment)
        this.sentiment = sentiment
    }

    fun openSentimentDialog() {
        sentiment?.let { sentiment ->
            _sentimentStateFlow.value = SentimentState.Selected(sentiment)
        } ?: analyzeSentiment()
    }

    private fun analyzeSentiment() {
        viewModelScope.launch {
            analyzeSentimentUseCase(AnalyzeSentimentUseCase.Request(contentFlow.value))
                .onSuccess { response ->
                    _sentimentStateFlow.value = SentimentState.Recommended(response.sentiment)
                    sentiment = response.sentiment
                }
                .onFailure {
                    // TODO("오류 처리")
                }
        }
    }

    fun closeSentimentDialog() {
        _sentimentStateFlow.value = SentimentState.Closed
    }

    fun save() {
        val sentiment = when (val sentimentState = sentimentStateFlow.value) {
            is SentimentState.Recommended -> sentimentState.sentiment
            is SentimentState.Selected -> sentimentState.sentiment
            else -> return
        }

        viewModelScope.launch {
            addDiaryUseCase(
                AddDiaryUseCase.Request(
                    date = dateFlow.value,
                    content = contentFlow.value,
                    sentiment = sentiment,
                    imageList = imageListFlow.value.map { it.toDomain() }
                )
            ).onSuccess { response ->
                _eventFlow.tryEmit(AddDiaryEvent.Success(response.diaryId))
            }.onFailure {
                // TODO("오류 처리")
            }
        }
    }

    companion object {
        private const val MAX_IMAGE_LIST_SIZE = 3
    }
}
