package com.bytephant.senior_care.ui.routing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bytephant.senior_care.domain.ChatbotAgent
import com.bytephant.senior_care.domain.status.StatusCollector
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    navController: NavHostController,
    chatbotAgent: ChatbotAgent,
    statusCollector: StatusCollector,
    body: @Composable (padding: PaddingValues) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope();
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    val items = listOf(
        NavigationItem(
            title = "홈",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = AppScreenType.HOME
        ),
        NavigationItem(
            title = "채팅",
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit,
            route = AppScreenType.CHAT
        ),
        NavigationItem(
            title = "기억",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            route = AppScreenType.HISTORY
        ),
        NavigationItem(
            title = "키워드",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite,
            route = AppScreenType.KEYWORD
        )
    )
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(0.95f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        items.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text = item.title,
                                        fontSize = 28.sp
                                    )
                                },
                                selected = index == selectedItemIndex,
                                onClick = {
                                    navController.navigate(item.route.name)
                                    selectedItemIndex = index
                                    scope.launch { drawerState.close() }
                                },
                                icon = {
                                    Icon(
                                        imageVector =
                                            if (index == selectedItemIndex) {
                                                item.selectedIcon
                                            } else item.unselectedIcon,
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        contentDescription = item.title
                                    )
                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = MaterialTheme.colorScheme.secondary
                                ),
                                modifier = Modifier
                                    .padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                    }
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { scope.launch { statusCollector.updateHomeLocation() } },
                            colors = ButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Black,
                            ),
                            modifier = Modifier
                                .background(Color.Transparent)
                                .fillMaxWidth(0.9f),
                        ) {
                            Text(text = "현재 위치를 집으로 설정하기")
                        }
                    }
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    currentScreenType = AppScreenType.HOME,
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier.size(36.dp),
                                tint = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    },
                    hiddenAction = {
                        scope.launch {
                            chatbotAgent.initTalking();
                        }
                    }
                )
            }
        ) { innerPadding ->
            body(innerPadding)
        }
    }
}