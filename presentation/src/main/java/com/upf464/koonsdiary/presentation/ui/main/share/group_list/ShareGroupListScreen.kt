package com.upf464.koonsdiary.presentation.ui.main.share.group_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.domain.model.User
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun ShareGroupListScreen(
    viewModel: ShareGroupListViewModel = hiltViewModel()
) {
    ShareGroupListScreen(
//        groupListState = viewModel.groupListStateFlow.collectAsState().value,
        groupListState = ShareGroupListState.Success(
            listOf(
                ShareGroup(
                    name = "테스트 그룹1",
                    imagePath = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg",
                    userList = listOf(
                        User(
                            username = "username1",
                            nickname = "닉네임1",
                            image = User.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                        ),
                        User(
                            username = "username2",
                            nickname = "닉네임2",
                            image = User.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                        ),
                        User(
                            username = "username3",
                            nickname = "닉네임3",
                            image = User.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                        )
                    )
                ),
                ShareGroup(
                    name = "테스트 그룹2",
                    imagePath = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg",
                    userList = listOf(
                        User(
                            username = "username4",
                            nickname = "닉네임4",
                            image = User.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                        ),
                        User(
                            username = "username5",
                            nickname = "닉네임5",
                            image = User.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                        ),
                        User(
                            username = "username6",
                            nickname = "닉네임6",
                            image = User.Image(path = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg")
                        )
                    )
                ),
                ShareGroup(
                    name = "테스트 그룹3",
                    imagePath = "https://i.pinimg.com/originals/3f/ba/d9/3fbad97c5829c3df9d857dae7857c7ce.jpg"
                ),
            )
        ),
        viewType = viewModel.viewTypeFlow.collectAsState().value,
        onChangeViewType = { viewModel.changeViewType(it) }
    )
}

@Composable
private fun ShareGroupListScreen(
    groupListState: ShareGroupListState = ShareGroupListState.Loading,
    viewType: ShareGroupListViewType = ShareGroupListViewType.PAGER,
    onChangeViewType: (ShareGroupListViewType) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ShareGroupTopBar(
            viewType = viewType,
            onChangeViewType = onChangeViewType
        )

        Column(modifier = Modifier.fillMaxSize()) {
            when (groupListState) {
                ShareGroupListState.Loading -> {}
                is ShareGroupListState.Success -> {
                    when (viewType) {
                        ShareGroupListViewType.PAGER ->
                            ShareGroupPager(groupList = groupListState.groupList)
                        ShareGroupListViewType.GRID ->
                            ShareGroupGrid(groupList = groupListState.groupList)
                    }
                }
            }
        }
    }
}

@Composable
private fun ShareGroupTopBar(
    viewType: ShareGroupListViewType,
    onChangeViewType: (ShareGroupListViewType) -> Unit
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        title = {
            Text(
                text = stringResource(id = R.string.shareDiary_of),
                style = KoonsTypography.H5,
                color = KoonsColor.Black100
            )
        },
        actions = {
            when (viewType) {
                ShareGroupListViewType.PAGER -> {
                    IconButton(onClick = { onChangeViewType(ShareGroupListViewType.GRID) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_grid),
                            contentDescription = null,
                            tint = KoonsColor.Green
                        )
                    }
                }
                ShareGroupListViewType.GRID -> {
                    IconButton(onClick = { onChangeViewType(ShareGroupListViewType.PAGER) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pager),
                            contentDescription = null,
                            tint = KoonsColor.Green
                        )
                    }
                }
            }
        }
    )
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun ColumnScope.ShareGroupPager(
    groupList: List<ShareGroup>
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        if (pagerState.currentPage > 0) {
            val currentGroup = groupList[pagerState.currentPage - 1]

            Text(
                text = "함께하는 친구들",
                style = KoonsTypography.BodyMedium,
                color = KoonsColor.Black100,
                modifier = Modifier.padding(start = 24.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(currentGroup.userList) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = item.image.path,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = item.nickname,
                            style = KoonsTypography.BodyMoreSmall,
                            color = KoonsColor.Black100,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }

    HorizontalPager(
        count = groupList.size + 1,
        state = pagerState
    ) { index ->
        if (index == 0) {
            Card(
                backgroundColor = KoonsColor.Black5,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier.height(IntrinsicSize.Min)) {
                    Column(
                        modifier = Modifier.padding(vertical = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(KoonsColor.Black40)
                        )
                        Text(
                            text = "새 공유 일기장 만들기",
                            style = KoonsTypography.H7,
                            color = KoonsColor.Black100,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterEnd)
                            .padding(end = 48.dp)
                            .width(12.dp)
                            .background(KoonsColor.Green)
                    )
                }
            }
        } else {
            val model = groupList[index - 1]
            Card(
                backgroundColor = KoonsColor.Black5,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier.height(IntrinsicSize.Min)) {
                    Column(
                        modifier = Modifier.padding(vertical = 48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = model.imagePath,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Text(
                            text = model.name,
                            style = KoonsTypography.H7,
                            color = KoonsColor.Black100,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterEnd)
                            .padding(end = 48.dp)
                            .width(12.dp)
                            .background(KoonsColor.Green)
                    )
                }
            }
        }
    }

    Box(modifier = Modifier.weight(1f))
}

@Composable
private fun ShareGroupGrid(
    groupList: List<ShareGroup>
) {

}
