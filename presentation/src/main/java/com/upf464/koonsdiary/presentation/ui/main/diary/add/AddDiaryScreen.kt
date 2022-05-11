package com.upf464.koonsdiary.presentation.ui.main.diary.add

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.model.diary.detail.DiaryImageModel
import com.upf464.koonsdiary.presentation.ui.theme.Black100
import java.time.LocalDate

private var galleryCallback: ((String) -> Unit)? = null

@Composable
internal fun AddDiaryScreen(
    viewModel: AddDiaryViewModel = hiltViewModel()
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
            onImageChanged = {
                galleryCallback = { uri ->
                    viewModel.changeImage(index, uri)
                }
                galleryLauncher.launch("image/*")
            },
            onImageDeleted = { viewModel.deleteImage(index) }
        )
    }

    AddDiaryScreen(
        availableDateState = viewModel.availableDateStateFlow.collectAsState().value,
        date = viewModel.dateFlow.collectAsState().value,
        imageList = viewModel.imageListFlow.collectAsState().value,
        sentimentState = viewModel.sentimentStateFlow.collectAsState().value,
        content = viewModel.contentFlow.collectAsState().value,
        onContentChange = { viewModel.contentFlow.value = it },
        onSave = { viewModel.analyzeSentiment() },
        onSelectImage = { galleryLauncher.launch("image/*") },
        onAddImage = { viewModel.addImage(it) },
        onImageClicked = { viewModel.openImageDialog(it) },
        onDateClicked = { viewModel.toggleAvailableDate() }
    )
}

@Composable
private fun AddDiaryScreen(
    availableDateState: AvailableDateState,
    date: LocalDate,
    imageList: List<DiaryImageModel>,
    sentimentState: SentimentState,
    content: String,
    onContentChange: (String) -> Unit,
    onSave: () -> Unit,
    onSelectImage: () -> Unit,
    onAddImage: (String) -> Unit,
    onImageClicked: (Int) -> Unit,
    onDateClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        AddDiaryTopBar(onSave = onSave)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            AddDiaryDate(date = date, onDateClicked = onDateClicked)
            AddImagePager(
                modelList = imageList,
                onSelectImage = onSelectImage,
                onAddImage = onAddImage,
                onImageClicked = onImageClicked
            )
            TextField(
                value = content,
                onValueChange = onContentChange,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
private fun AddDiaryTopBar(
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
private fun AddDiaryDate(
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
private fun AddImagePager(
    modelList: List<DiaryImageModel>,
    onSelectImage: () -> Unit,
    onAddImage: (String) -> Unit,
    onImageClicked: (Int) -> Unit
) {
    HorizontalPager(count = modelList.size + 1) { index ->
        val isButton = index == modelList.size

        Card(
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .then(if (isButton) Modifier.clickable {
                    galleryCallback = onAddImage
                    onSelectImage()
                } else Modifier)
        ) {
            if (isButton) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            } else {
                val model = modelList[index]
                val content = model.content.collectAsState()
                Column {
                    AsyncImage(
                        model = model.imagePath,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                onImageClicked(index)
                            }
                    )
                    TextField(
                        value = content.value,
                        onValueChange = { model.content.value = it },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChangeImageDialog(
    onDismiss: () -> Unit,
    onImageChanged: () -> Unit,
    onImageDeleted: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("해당 이미지를 변경하시겠습니까?") },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onImageDeleted) {
                    Text(text = "삭제")
                }
                TextButton(onClick = onImageChanged) {
                    Text(text = "재선택")
                }
            }
        }
    )
}
