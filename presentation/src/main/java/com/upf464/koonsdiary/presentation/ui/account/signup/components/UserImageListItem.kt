package com.upf464.koonsdiary.presentation.ui.account.signup.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.upf464.koonsdiary.domain.model.User

@Composable
internal fun UserImageListItem(
    imageModel: User.Image,
    onItemClick: () -> Unit
) {
    AsyncImage(
        model = imageModel.path,
        contentDescription = null,
        modifier = Modifier
            .clickable { onItemClick() }
            .aspectRatio(1f)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}
