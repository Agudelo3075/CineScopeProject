package com.desarrollox.cinescopeproyect

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme
import com.desarrollox.cinescopeproyect.ui.viewmodel.ProfileViewModel

private val BgDark      = Color(0xFF120A0A)
private val RedMain     = Color(0xFFE53935)
private val RedDark     = Color(0xFFB71C1C)
private val TextWhite   = Color(0xFFF5F5F5)
private val TextGray    = Color(0xFF9E8E8E)
private val CardBg      = Color(0xFF1E1414)

@Composable
private fun PerfilStatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(value, color = TextWhite, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
    }
}

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                val viewModel: ProfileViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val context = LocalContext.current
                
                LaunchedEffect(uiState.logoutSuccess) {
                    if (uiState.logoutSuccess) {
                        viewModel.resetLogoutSuccess()
                        context.startActivity(Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                        finish()
                    }
                }
                
                PerfilScreen(
                    userName = uiState.user?.fullName ?: "User",
                    userEmail = uiState.user?.email ?: "",
                    userInitials = uiState.user?.avatarInitials ?: "U",
                    viewsCount = uiState.viewsCount,
                    favoritesCount = uiState.favoritesCount,
                    isLoading = uiState.isLoading,
                    onLogout = { viewModel.logout() },
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun PerfilScreen(
    userName: String = "User",
    userEmail: String = "",
    userInitials: String = "U",
    viewsCount: Int = 0,
    favoritesCount: Int = 0,
    isLoading: Boolean = false,
    onLogout: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = RedMain)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "←",
                        color = TextWhite,
                        fontSize = 22.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .clickable { onBack() }
                    )
                    Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(RedMain, RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🎬", fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "CineScope",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                }

                // Botón de menú
                Text(
                    text = "⋯",
                    color = TextWhite,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { }
                )
            }

            // ── HERO SECTION (MISMO diseño de cine que RegisterActivity) ─────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            ) {
                // Fondo con degradado
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF2A0808),
                                    Color(0xFF4A0D0D),
                                    Color(0xFF3D0A0A),
                                    Color(0xFF1A0505)
                                )
                            )
                        )
                )

                // Brillo radial
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0x44FFFFFF), Color(0x00000000)),
                                radius = 280f
                            )
                        )
                )

                // Butacas (mismo diseño)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ) {
                    repeat(3) { row ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = (row * 6).dp)
                                .padding(bottom = 3.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            repeat(11 - row) {
                                Box(
                                    modifier = Modifier
                                        .width(26.dp)
                                        .height(16.dp)
                                        .background(
                                            Color(0xFF8B0000).copy(alpha = 0.75f + row * 0.08f),
                                            RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                        )
                                )
                            }
                        }
                    }
                }

                // Degradado inferior
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .align(Alignment.BottomCenter)
                        .background(Brush.verticalGradient(listOf(Color.Transparent, BgDark)))
                )

                // Texto hero
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = "Your Profile",
                        color = TextWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Manage your account and preferences",
                        color = TextGray,
                        fontSize = 13.sp
                    )
                }
            }

            // ── PERFIL (Avatar y datos) ─────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar con gradiente (mismo estilo que las butacas)
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(RedDark, RedMain)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        userInitials,
                        color = TextWhite,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    userName,
                    color = TextWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    userEmail,
                    color = RedMain,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── TARJETAS DE ESTADÍSTICAS ────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PerfilStatCard("VISTAS", viewsCount.toString(), Modifier.weight(1f))
                PerfilStatCard("FAVORITAS", favoritesCount.toString(), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── OPCIÓN DE AJUSTES ───────────────────────────────────────────────
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(RedMain),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("⚙️", fontSize = 22.sp)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        "Ajustes",
                        color = TextWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "›",
                        color = TextGray,
                        fontSize = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── BOTÓN DE CERRAR SESIÓN ──────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🚪", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "CERRAR SESIÓN",
                    color = RedMain,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Bottom bar con padding inferior
        Spacer(modifier = Modifier.height(80.dp))
    }
}
}