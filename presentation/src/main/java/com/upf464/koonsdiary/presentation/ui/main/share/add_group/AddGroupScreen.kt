package com.upf464.koonsdiary.presentation.ui.main.share.add_group

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun AddGroupScreen(
    viewModel: AddGroupViewModel = hiltViewModel()
) {
    AddGroupScreen(
        onSave = { viewModel.save() }
    )
}

@Composable
private fun AddGroupScreen(
    onSave: () -> Unit = { },
    imagePath: String? = null,
    groupName: String = "",
    onGroupNameChange: (String) -> Unit = { },
    onImageClicked: () -> Unit = { }
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AddGroupAppBar(onSave = onSave)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            GroupInformation(
                imagePath = imagePath,
                groupName = groupName,
                onGroupNameChange = onGroupNameChange,
                onImageClicked = onImageClicked
            )
        }
    }
}

@Composable
private fun AddGroupAppBar(
    onSave: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "새 공유 일기장",
                style = KoonsTypography.H3,
                color = KoonsColor.Black100
            )
        },
        actions = {
            IconButton(onClick = onSave) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = KoonsColor.Green
                )
            }
        }
    )
}

@Composable
private fun GroupInformation(
    imagePath: String?,
    groupName: String,
    onGroupNameChange: (String) -> Unit,
    onImageClicked: () -> Unit
) {
    Card(
        backgroundColor = KoonsColor.Black5,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, KoonsColor.Black100),
        modifier = Modifier
            .padding(start = 48.dp, end = 48.dp, top = 72.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.height(IntrinsicSize.Min)) {
            Column(
                modifier = Modifier.padding(vertical = 48.dp, horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imagePath == null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(KoonsColor.Black40)
                            .clickable(onClick = onImageClicked)
                    )
                } else {
                    AsyncImage(
                        model = imagePath,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(onClick = onImageClicked)
                    )
                }

                BasicTextField(
                    value = groupName,
                    onValueChange = onGroupNameChange,
                    textStyle = KoonsTypography.H7.copy(textAlign = TextAlign.Center),
                    decorationBox = { innerTextField ->
                        if (groupName.isEmpty()) {
                            Text(
                                text = "공유 일기장 이름",
                                color = KoonsColor.Black40,
                                style = KoonsTypography.H7,
                                textAlign = TextAlign.Center
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(6f))
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(12.dp)
                        .background(KoonsColor.Green)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
