package com.upf464.koonsdiary.presentation.ui.main.diary.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.domain.usecase.calendar.FetchCalendarFlowUseCase
import com.upf464.koonsdiary.domain.usecase.diary.AddDiaryUseCase
import com.upf464.koonsdiary.domain.usecase.diary.AnalyzeSentimentUseCase
import com.upf464.koonsdiary.domain.usecase.diary.FetchDiaryUseCase
import com.upf464.koonsdiary.domain.usecase.diary.UpdateDiaryUseCase
import com.upf464.koonsdiary.presentation.common.Constants
import com.upf464.koonsdiary.presentation.mapper.toDomain
import com.upf464.koonsdiary.presentation.mapper.toEditorModel
import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryImageModel
import com.upf464.koonsdiary.presentation.model.diary.editor.DiaryEditorModel
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
internal class DiaryEditorViewModel @Inject constructor(
    private val addDiaryUseCase: AddDiaryUseCase,
    private val updateDiaryUseCase: UpdateDiaryUseCase,
    private val analyzeSentimentUseCase: AnalyzeSentimentUseCase,
    private val fetchCalendarFlowUseCase: FetchCalendarFlowUseCase,
    private val fetchDiaryUseCase: FetchDiaryUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var fetchCalendarJob: Job? = null

    private val _availableDateStateFlow = MutableStateFlow<AvailableDateState>(AvailableDateState.Closed)
    val availableDateStateFlow = _availableDateStateFlow.asStateFlow()

    private val _dateFlow = MutableStateFlow(savedStateHandle.get<String>(Constants.PARAM_DATE)?.let {
        LocalDate.parse(it)
    } ?: LocalDate.now())
    private val _imageListFlow = MutableStateFlow<List<DiaryImageModel>>(emptyList())
    val contentFlow = MutableStateFlow("")

    val model = DiaryEditorModel(
        diaryId = savedStateHandle.get<Int>(Constants.PARAM_DIARY_ID),
        dateFlow = _dateFlow.asStateFlow(),
        imageListFlow = _imageListFlow.asStateFlow(),
        contentFlow = contentFlow.asStateFlow(),
        sentiment = null
    )

    val showAddImageFlow = model.imageListFlow.map { it.size < MAX_IMAGE_LIST_SIZE }
    private val _imageDialogStateFlow = MutableStateFlow<ImageDialogState>(ImageDialogState.Closed)
    val imageDialogStateFlow = _imageDialogStateFlow.asStateFlow()

    private val _sentimentStateFlow = MutableStateFlow<SentimentState>(SentimentState.Closed)
    val sentimentStateFlow = _sentimentStateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<DiaryEditorEvent>(extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    private val _loadingStateFlow = MutableStateFlow(
        if (model.diaryId != null) LoadingState.Loading
        else LoadingState.Closed
    )
    val loadingStateFlow = _loadingStateFlow.asStateFlow()

    init {
        model.diaryId?.let { diaryId ->
            viewModelScope.launch {
                fetchDiaryUseCase(FetchDiaryUseCase.Request(diaryId))
                    .onSuccess { response ->
                        val diary = response.diary
                        _dateFlow.value = diary.date
                        _imageListFlow.value = diary.imageList.map { it.toEditorModel() }
                        contentFlow.value = diary.content
                        model.sentiment = diary.sentiment
                        _loadingStateFlow.value = LoadingState.Closed
                    }
                    .onFailure {
                        _loadingStateFlow.value = LoadingState.Error
                    }
            }
        }
    }

    fun selectDate(day: Int) {
        val dateState = _availableDateStateFlow.value
        if (dateState is AvailableDateState.Success) {
            _dateFlow.value = LocalDate.of(dateState.year, dateState.month, day)
        }

        _availableDateStateFlow.value = AvailableDateState.Closed
    }

    fun toggleAvailableDate() {
        if (availableDateStateFlow.value == AvailableDateState.Closed) {
            val date = model.dateFlow.value
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
        val imageList = model.imageListFlow.value
        _imageListFlow.value = imageList.subList(0, index) + imageList.subList(index + 1, imageList.size)
        _imageDialogStateFlow.value = ImageDialogState.Closed
    }

    fun startSelectSentiment() {
        _sentimentStateFlow.value = SentimentState.InSelect
    }

    fun selectSentiment(sentiment: Sentiment) {
        _sentimentStateFlow.value = SentimentState.Selected(sentiment)
        model.sentiment = sentiment
    }

    fun openSentimentDialog() {
        model.sentiment?.let { sentiment ->
            _sentimentStateFlow.value = SentimentState.Selected(sentiment)
        } ?: analyzeSentiment()
    }

    private fun analyzeSentiment() {
        viewModelScope.launch {
            analyzeSentimentUseCase(AnalyzeSentimentUseCase.Request(contentFlow.value))
                .onSuccess { response ->
                    _sentimentStateFlow.value = SentimentState.Recommended(response.sentiment)
                    model.sentiment = response.sentiment
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
        viewModelScope.launch {
            if (model.diaryId == null) {
                addDiaryUseCase(
                    AddDiaryUseCase.Request(
                        date = model.dateFlow.value,
                        content = model.contentFlow.value,
                        sentiment = model.sentiment ?: return@launch,
                        imageList = model.imageListFlow.value.map { it.toDomain() }
                    )
                ).onSuccess { response ->
                    _eventFlow.tryEmit(DiaryEditorEvent.Success(response.diaryId))
                }.onFailure {
                    // TODO("오류 처리")
                }
            } else {
                updateDiaryUseCase(
                    UpdateDiaryUseCase.Request(
                        diaryId = model.diaryId,
                        date = model.dateFlow.value,
                        content = model.contentFlow.value,
                        sentiment = model.sentiment ?: return@launch,
                        imageList = model.imageListFlow.value.map { it.toDomain() }
                    )
                ).onSuccess { response ->
                    _eventFlow.tryEmit(DiaryEditorEvent.Success(response.diaryId))
                }.onFailure {
                    // TODO("오류 처리")
                }
            }
        }
    }

    companion object {
        private const val MAX_IMAGE_LIST_SIZE = 3
    }
}
