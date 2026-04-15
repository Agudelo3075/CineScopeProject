package com.desarrollox.cinescopeproyect.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

private val BgNavBar = Color(0xFF111111)
private val BorderNav = Color(0xFF2A2A2A)
private val RedMain = Color(0xFFE50914)
private val TextGray = Color(0xFF9E9E9E)

enum class BottomBarItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    Inicio(Screen.Dashboard.route, Icons.Default.Home, "Inicio"),
    Historial(Screen.Historial.route, Icons.Default.History, "Historial"),
    MiLista(Screen.MiLista.route, Icons.AutoMirrored.Filled.ViewList, "Mi Lista"),
    Favoritos(Screen.Favoritos.route, Icons.Default.Favorite, "Favoritos"),
    Perfil(Screen.Perfil.route, Icons.Default.Person, "Perfil")
}

@Composable
fun CineScopeBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // No mostrar la barra si estamos en Login o Register
    if (currentRoute == Screen.Login.route || currentRoute == Screen.Register.route) {
        return
    }

    BottomBarContainer(modifier = modifier) {
        BottomBarItem.values().forEach { item ->
            BottomBarIcon(
                icon = item.icon,
                label = item.label,
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CineScopeBottomBar(
    context: Context,
    selected: BottomRoute,
    modifier: Modifier = Modifier
) {
    BottomBarContainer(modifier = modifier) {
        BottomBarItem.values().forEach { item ->
            BottomBarIcon(
                icon = item.icon,
                label = item.label,
                selected = selected.route == item.route,
                onClick = {
                    context.navigateToBottomRoute(
                        when (item) {
                            BottomBarItem.Inicio -> BottomRoute.Inicio
                            BottomBarItem.Historial -> BottomRoute.Historial
                            BottomBarItem.MiLista -> BottomRoute.MiLista
                            BottomBarItem.Favoritos -> BottomRoute.Favoritos
                            BottomBarItem.Perfil -> BottomRoute.Perfil
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun BottomBarContainer(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BgNavBar)
                .navigationBarsPadding()
        ) {
            HorizontalDivider(color = BorderNav, thickness = 0.5.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                content = content
            )
        }
    }
}

@Composable
private fun BottomBarIcon(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) RedMain else TextGray,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.height(3.dp))
        Text(
            text = label,
            color = if (selected) RedMain else TextGray,
            fontSize = 9.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
