package com.upf464.koonsdiary.presentation.ui.main.diary.editor

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.upf464.koonsdiary.domain.model.Sentiment
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryImageModel
import com.upf464.koonsdiary.presentation.ui.main.diary.DiaryNavigation
import com.upf464.koonsdiary.presentation.ui.theme.Black100
import com.upf464.koonsdiary.presentation.ui.theme.colorOf
import com.upf464.koonsdiary.presentation.ui.theme.transparentTextColors
import java.time.LocalDate

private var galleryCallback: ((String) -> Unit)? = null

@Composable
internal fun DiaryEditorScreen(
    viewModel: DiaryEditorViewModel = hiltViewModel(),
    navController: NavController
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            galleryCallback?.invoke(uri.toString())
        }
    }

    val dialogState = viewModel.imageDialogStateFlow.collectAsState().value

    if (dialogState is ImageDialogState.Opened) {
        val index = dialogState.index
        ChangeImageDialog(
            onDismiss = { viewModel.closeImageDialog() },
            onImageDeleted = { viewModel.deleteImage(index) }
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is DiaryEditorEvent.Success -> {
                    if (viewModel.model.diaryId != null) {
                        navController.popBackStack()
                    }
                    navController.popBackStack()
                    navController.navigate(DiaryNavigation.DETAIL.route + "/${event.diaryId}")
                }
            }
        }
    }

    when (viewModel.loadingStateFlow.collectAsState().value) {
        DiaryLoadingState.Loading -> LoadingDialog()
        DiaryLoadingState.Error -> LoadingDialog(isError = true)
        DiaryLoadingState.Closed -> {}
    }

    SentimentDialog(
        state = viewModel.sentimentStateFlow.collectAsState().value,
        onDismiss = { viewModel.closeSentimentDialog() },
        onStartSelect = { viewModel.startSelectSentiment() },
        onSentimentSelected = { viewModel.selectSentiment(it) },
        onConfirmed = { viewModel.save() }
    )

    DiaryEditorScreen(
        availableDateState = viewModel.availableDateStateFlow.collectAsState().value,
        date = viewModel.model.dateFlow.collectAsState().value,
        imageList = viewModel.model.imageListFlow.collectAsState().value,
        content = viewModel.contentFlow.collectAsState().value,
        onContentChange = { viewModel.contentFlow.value = it },
        onSave = { viewModel.openSentimentDialog() },
        showAddImage = viewModel.showAddImageFlow.collectAsState(initial = true).value,
        onSelectImage = { galleryLauncher.launch("image/*") },
        onAddImage = { viewModel.addImage(it) },
        onImageClicked = { viewModel.openImageDialog(it) },
        onDateClicked = { viewModel.toggleAvailableDate() }
    )
}

@Composable
private fun LoadingDialog(
    isError: Boolean = false
) {
    Dialog(
        onDismissRequest = {},
        DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(
                    horizontal = 16.dp,
                    vertical = 32.dp
                )
        ) {
            if (isError) {
                Text(text = "오류가 발생했습니다.")
            } else {
                Text(text = "로딩중입니다...")
            }
        }
    }
}

@Composable
private fun DiaryEditorScreen(
    availableDateState: AvailableDateState,
    date: LocalDate,
    imageList: List<DiaryImageModel>,
    content: String,
    onContentChange: (String) -> Unit,
    onSave: () -> Unit,
    showAddImage: Boolean,
    onSelectImage: () -> Unit,
    onAddImage: (String) -> Unit,
    onImageClicked: (Int) -> Unit,
    onDateClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        DiaryEditorTopBar(onSave = onSave)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            DiaryEditorDate(date = date, onDateClicked = onDateClicked)
            ImageLazyList(
                modelList = imageList,
                showAddImage = showAddImage,
                onSelectImage = onSelectImage,
                onAddImage = onAddImage,
                onImageClicked = onImageClicked
            )
            TextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 300.dp),
                colors = transparentTextColors()
            )
        }
    }
}

@Composable
private fun DiaryEditorTopBar(
    onSave: () -> Unit
) {
    TopAppBar(
        title = { },
        actions = {
            IconButton(onClick = onSave) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun DiaryEditorDate(
    date: LocalDate,
    onDateClicked: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(
            modifier = Modifier
                .weight(1f)
                .background(Black100)
        )
        Text(
            text = stringResource(
                id = R.string.year_month_day,
                date.year,
                date.monthValue,
                date.dayOfMonth
            ),
            modifier = Modifier.clickable(onClick = onDateClicked)
        )
        Divider(
            modifier = Modifier
                .weight(1f)
                .background(Black100)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ImageLazyList(
    modelList: List<DiaryImageModel>,
    showAddImage: Boolean,
    onSelectImage: () -> Unit,
    onAddImage: (String) -> Unit,
    onImageClicked: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(count = modelList.size) { index ->
            Card(elevation = 4.dp) {
                val model = modelList[index]
                val content = model.content.collectAsState()

                AsyncImage(
                    model = model.imagePath,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(180.dp)
                        .clickable {
                            onImageClicked(index)
                        },
                    contentScale = ContentScale.FillHeight
                )
            }
        }

        if (showAddImage) {
            item {
                Card(elevation = 4.dp) {
                    Box(
                        modifier = Modifier
                            .height(196.dp)
                            .clickable {
                                galleryCallback = onAddImage
                                onSelectImage()
                            }
                            .padding(horizontal = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChangeImageDialog(
    onDismiss: () -> Unit,
    onImageDeleted: () -> Unit
) {
    CustomDialog(
        onDismiss = onDismiss,
        onConfirmed = onImageDeleted
    ) {
        Text(
            text = buildAnnotatedString {
                append("정말로 사진을 ")
                withStyle(SpanStyle(color = Color.Red)) { append("삭제") }
                append("하시나요?")
            }
        )
    }
}

@Composable
private fun SentimentDialog(
    state: SentimentState,
    onDismiss: () -> Unit,
    onStartSelect: () -> Unit,
    onSentimentSelected: (Sentiment) -> Unit,
    onConfirmed: () -> Unit
) {
    when (state) {
        is SentimentState.Selected -> {
            CustomDialog(
                onDismiss = onDismiss,
                onNegative = onStartSelect,
                onConfirmed = onConfirmed
            ) {
                Text(
                    text = "콩돌님이 선택한 감정은",
                    modifier = Modifier.fillMaxWidth()
                )
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_koon
                    ),
                    tint = colorOf(state.sentiment),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(48.dp)
                )
            }
        }
        is SentimentState.Recommended -> {
            CustomDialog(
                onDismiss = onDismiss,
                onNegative = onStartSelect,
                onConfirmed = onConfirmed
            ) {
                Text(
                    text = "쿤이가 생각하는 콩돌님의 감정은",
                    modifier = Modifier.fillMaxWidth()
                )
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_koon
                    ),
                    tint = colorOf(state.sentiment),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(48.dp)
                )
            }
        }
        SentimentState.InSelect -> {
            CustomDialog(
                onDismiss = onDismiss,
                onConfirmed = onConfirmed,
                noButtons = true
            ) {
                Text(
                    text = "콩돌님의 감정을 선택해주세요.",
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Sentiment.values().forEach { sentiment ->
                        Icon(
                            painter = painterResource(
                                id = R.drawable.ic_koon
                            ),
                            tint = colorOf(sentiment),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                onSentimentSelected(sentiment)
                            }
                        )
                    }
                }
            }
        }
        SentimentState.Closed -> return
    }
}

@Composable
private fun CustomDialog(
    onDismiss: () -> Unit = { },
    onNegative: () -> Unit = onDismiss,
    onConfirmed: (() -> Unit) = { },
    noButtons: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                content()
                if (!noButtons) {
                    DialogButtons(
                        onNegative = onNegative,
                        onConfirmed = onConfirmed
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DialogButtons(
    onNegative: () -> Unit,
    onConfirmed: () -> Unit
) {
    Row(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CompositionLocalProvider(
            LocalMinimumTouchTargetEnforcement provides false,
        ) {
            Button(
                onClick = onNegative,
                border = BorderStroke(1.dp, Color.Green),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                contentPadding = PaddingValues(all = 0.dp)
            ) {
                Text(text = "아니요", color = Color.Green)
            }
            Button(
                onClick = onConfirmed,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green, contentColor = Color.White),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(all = 0.dp)
            ) {
                Text(text = "예")
            }
        }
    }
}
