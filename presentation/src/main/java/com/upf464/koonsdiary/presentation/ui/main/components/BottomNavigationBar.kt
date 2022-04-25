package com.upf464.koonsdiary.presentation.ui.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    itemList: List<MainNavigationItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (MainNavigationItem) -> Unit
) {
    val backStackState = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier
    ) {
        itemList.forEach { item ->
            val selected = item.route == backStackState.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            )
        }
    }
}
