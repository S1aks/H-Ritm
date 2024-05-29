package com.s1aks.h_ritm.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.s1aks.h_ritm.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerHeader() {
    Text(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        text = stringResource(id = R.string.app_name),
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .size(100.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .matchParentSize(),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "",
        )
    }
}

sealed class MainNavItem(
    val title: String,
    val icon: ImageVector?,
    val route: String,
    val isDivider: Boolean = false,
) {
    object Spacer : MainNavItem("", null, "", true)
    object Settings : MainNavItem("Настройки", Icons.Default.Settings, Screen.Settings.route)
    object Export : MainNavItem("Экспорт данных", Icons.Default.Share, "")
    object Exit : MainNavItem("Выход", Icons.AutoMirrored.Filled.ExitToApp, "Exit")
}

@Composable
fun DrawerItem(
    item: MainNavItem,
    selected: Boolean,
    onItemClick: () -> Unit
) {
    if (item.isDivider) {
        Spacer(
            modifier = Modifier.height(20.dp)
        )
    } else {
        Button(
            modifier = Modifier
                .height(50.dp)
                .padding(horizontal = 10.dp, vertical = 2.dp)
                .fillMaxWidth(),
            shape = RectangleShape,
            enabled = selected,
            onClick = { onItemClick() })
        {
            Icon(
                modifier = Modifier
                    .scale(1.2f)
                    .offset(x = -10.dp, y = 0.dp),
                imageVector = item.icon
                    ?: throw RuntimeException("Ошибка иконки бокового меню"),
                contentDescription = item.title
            )
            Text(
                text = item.title, fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun DrawerContent(
    drawerItems: List<MainNavItem> = listOf(
        MainNavItem.Spacer,
        MainNavItem.Settings,
        MainNavItem.Export,
        MainNavItem.Spacer,
        MainNavItem.Exit
    ),
    navController: NavController? = null,
    scope: CoroutineScope? = null,
    drawerState: DrawerState? = null,
    onExit: () -> Unit = {}
) {
    val navBackStackEntry = navController?.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.value?.destination?.route
    val itemClick: (MainNavItem) -> Unit = { item ->
        if (item.route == "Exit") {
            onExit()
        } else {
            if (item !is MainNavItem.Spacer) {
                navController?.navigate(item.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        scope?.launch {
            drawerState?.close()
        }
    }
    Column(
        modifier = Modifier.width(250.dp)
    ) {
        DrawerHeader()
        LazyColumn {
            items(drawerItems) { item ->
                DrawerItem(
                    item = item,
                    selected = currentRoute != item.route,
                    onItemClick = { itemClick(item) }
                )
            }
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = "S1aks @ 2024",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDrawerContent() {
    DrawerContent()
}