package com.desarrollox.cinescopeproyect.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BgNavBar = Color(0xFF111111)
private val BorderNav = Color(0xFF2A2A2A)
private val RedMain = Color(0xFFE50914)
private val TextGray = Color(0xFF9E9E9E)

@Composable
fun CineScopeBottomBar(
    context: Context,
    selected: BottomRoute,
    modifier: Modifier = Modifier
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
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomBarItem(
                    icon = Icons.Default.Home,
                    label = "Inicio",
                    selected = selected == BottomRoute.Inicio,
                    onClick = { context.navigateToBottomRoute(BottomRoute.Inicio) }
                )
                BottomBarItem(
                    icon = Icons.Default.History,
                    label = "Historial",
                    selected = selected == BottomRoute.Historial,
                    onClick = { context.navigateToBottomRoute(BottomRoute.Historial) }
                )
                BottomBarItem(
                    icon = Icons.AutoMirrored.Filled.ViewList,
                    label = "Mi Lista",
                    selected = selected == BottomRoute.MiLista,
                    onClick = { context.navigateToBottomRoute(BottomRoute.MiLista) }
                )
                BottomBarItem(
                    icon = Icons.Default.Favorite,
                    label = "Favoritos",
                    selected = selected == BottomRoute.Favoritos,
                    onClick = { context.navigateToBottomRoute(BottomRoute.Favoritos) }
                )
                BottomBarItem(
                    icon = Icons.Default.Person,
                    label = "Perfil",
                    selected = selected == BottomRoute.Perfil,
                    onClick = { context.navigateToBottomRoute(BottomRoute.Perfil) }
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
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
