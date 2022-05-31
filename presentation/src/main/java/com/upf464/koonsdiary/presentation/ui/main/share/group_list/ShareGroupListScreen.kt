package com.upf464.koonsdiary.presentation.ui.main.share.group_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.upf464.koonsdiary.domain.model.ShareGroup
import com.upf464.koonsdiary.presentation.R
import com.upf464.koonsdiary.presentation.ui.main.share.ShareNavigation
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
internal fun ShareGroupListScreen(
    viewModel: ShareGroupListViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ShareGroupListEvent.NavigateToGroup -> {
                    navController.navigate(ShareNavigation.GROUP_DETAIL.route + "/${event.groupId}")
                }
                ShareGroupListEvent.NavigateToAddGroup -> {
                    navController.navigate(ShareNavigation.ADD_GROUP.route)
                }
            }
        }
    }

    ShareGroupListScreen(
        groupListState = viewModel.groupListStateFlow.collectAsState().value,
        viewType = viewModel.viewTypeFlow.collectAsState().value,
        onChangeViewType = { viewModel.changeViewType(it) },
        onItemClicked = { viewModel.navigateToGroup(it) }
    )
}

@Composable
private fun ShareGroupListScreen(
    groupListState: ShareGroupListState = ShareGroupListState.Loading,
    viewType: ShareGroupListViewType = ShareGroupListViewType.PAGER,
    onChangeViewType: (ShareGroupListViewType) -> Unit = {},
    onItemClicked: (ShareGroup?) -> Unit = {}
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
                            ShareGroupPager(
                                groupList = groupListState.groupList,
                                onItemClicked = onItemClicked
                            )
                        ShareGroupListViewType.GRID ->
                            ShareGroupGrid(
                                groupList = groupListState.groupList,
                                onItemClicked = onItemClicked
                            )
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
    groupList: List<ShareGroup>,
    onItemClicked: (ShareGroup?) -> Unit
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
            ShareGroupItem(
                viewType = ShareGroupListViewType.PAGER,
                model = null,
                paddingHorizontal = 32.dp,
                paddingVertical = 48.dp,
                onItemClicked = { onItemClicked(null) }
            )
        } else {
            ShareGroupItem(
                viewType = ShareGroupListViewType.PAGER,
                model = groupList[index - 1],
                paddingHorizontal = 32.dp,
                paddingVertical = 48.dp,
                onItemClicked = { onItemClicked(groupList[index - 1]) }
            )
        }
    }

    Box(modifier = Modifier.weight(1f))
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ShareGroupGrid(
    groupList: List<ShareGroup>,
    onItemClicked: (ShareGroup?) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ShareGroupItem(
                viewType = ShareGroupListViewType.GRID,
                model = null,
                paddingHorizontal = 24.dp,
                paddingVertical = 32.dp,
                onItemClicked = { onItemClicked(null) }
            )
        }

        items(groupList) { model ->
            ShareGroupItem(
                viewType = ShareGroupListViewType.GRID,
                model = model,
                paddingHorizontal = 24.dp,
                paddingVertical = 32.dp,
                onItemClicked = { onItemClicked(model) }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun ShareGroupItem(
    viewType: ShareGroupListViewType,
    model: ShareGroup?,
    paddingHorizontal: Dp,
    paddingVertical: Dp,
    onItemClicked: () -> Unit
) {
    Card(
        backgroundColor = KoonsColor.Black5,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, KoonsColor.Black100),
        modifier = when (viewType) {
            ShareGroupListViewType.PAGER -> Modifier.padding(horizontal = 48.dp)
            ShareGroupListViewType.GRID -> Modifier
        }.fillMaxWidth(),
        onClick = onItemClicked
    ) {
        Box(modifier = Modifier.height(IntrinsicSize.Min)) {
            Column(
                modifier = Modifier.padding(vertical = paddingVertical),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (model == null) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = paddingHorizontal)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(KoonsColor.Black40)
                    )
                } else {
                    AsyncImage(
                        model = model.imagePath,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(horizontal = paddingHorizontal)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }

                Text(
                    text = model?.name ?: "새 공유 일기장 만들기",
                    style = KoonsTypography.H7,
                    color = KoonsColor.Black100,
                    modifier = Modifier.padding(top = 16.dp)
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
