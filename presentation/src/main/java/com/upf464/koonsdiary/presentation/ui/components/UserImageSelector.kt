package com.upf464.koonsdiary.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun UserImageSelector(
    onImageClicked: (Int) -> Unit,
    imageList: List<User.Image>,
    selectedIndex: Int,
    paddingValues: PaddingValues = PaddingValues(24.dp),
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = paddingValues
    ) {
        items(imageList.size) { index ->
            AsyncImage(
                model = imageList[index].path,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .clickable { onImageClicked(index) }
                    .then(
                        if (selectedIndex == index) Modifier.border(width = 4.dp, color = KoonsColor.Green, shape = CircleShape)
                        else Modifier
                    ),
                contentScale = ContentScale.Crop
            )
        }
    }
}
