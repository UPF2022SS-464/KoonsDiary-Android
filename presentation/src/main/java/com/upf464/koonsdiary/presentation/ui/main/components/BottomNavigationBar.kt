package com.upf464.koonsdiary.presentation.ui.main.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import com.upf464.koonsdiary.presentation.ui.theme.KoonsColor
import com.upf464.koonsdiary.presentation.ui.theme.KoonsTypography

@Composable
fun BottomNavigationBar(
    itemList: List<MainNavigationItem>,
    onItemClick: (MainNavigationItem) -> Unit,
    backStackState: State<NavBackStackEntry?>
) {
    BottomNavigation(
        backgroundColor = KoonsColor.Black5
    ) {
        itemList.forEach { item ->
            val isSelected = backStackState.value?.destination?.route == item.route
            BottomNavigationItem(
                selected = isSelected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.name
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        style = KoonsTypography.BodySmall
                    )
                },
                alwaysShowLabel = false,
                unselectedContentColor = KoonsColor.Black20,
                selectedContentColor = KoonsColor.Green
            )
        }
    }
}
