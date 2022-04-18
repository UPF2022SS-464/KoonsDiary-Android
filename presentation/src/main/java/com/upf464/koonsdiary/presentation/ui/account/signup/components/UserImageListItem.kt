package com.upf464.koonsdiary.presentation.ui.account.signup.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.upf464.koonsdiary.presentation.model.account.UserImageModel

@Composable
internal fun UserImageListItem(
    imageModel: UserImageModel,
    onItemClick: () -> Unit
) {
    val selectState = imageModel.selectedFlow.collectAsState()
    AsyncImage(
        model = imageModel.path,
        contentDescription = null,
        modifier = Modifier
            .clickable { onItemClick() }
            .then(
                if (selectState.value)
                    Modifier.border(2.dp, color = Color.Green, shape = CircleShape)
                else Modifier
            )
            .aspectRatio(1f)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}
