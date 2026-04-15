package com.desarrollox.cinescopeproyect

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
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
import com.desarrollox.cinescopeproyect.data.local.entity.WatchHistoryEntity
import com.desarrollox.cinescopeproyect.navigation.navigateToMovieDetail
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme
import com.desarrollox.cinescopeproyect.ui.viewmodel.HistoryViewModel

private val BgDark      = Color(0xFF120A0A)
private val RedMain     = Color(0xFFE53935)
private val RedDark     = Color(0xFFB71C1C)
private val TextWhite   = Color(0xFFF5F5F5)
private val TextGray    = Color(0xFF9E8E8E)
private val CardBg      = Color(0xFF1E1414)

class HistorialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                val viewModel: HistoryViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                HistorialScreen(
                    movies = uiState.movies,
                    series = uiState.series,
                    selectedTab = uiState.selectedTab,
                    isLoading = uiState.isLoading,
                    onTabSelect = { viewModel.selectTab(it) },
                    onRemoveFromHistory = { viewModel.removeFromHistory(it) },
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun HistorialScreen(
    movies: List<WatchHistoryEntity> = emptyList(),
    series: List<WatchHistoryEntity> = emptyList(),
    selectedTab: Int = 0,
    isLoading: Boolean = false,
    onTabSelect: (Int) -> Unit = {},
    onRemoveFromHistory: (Long) -> Unit = {},
    onBack: () -> Unit = {},
    onMovieClick: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableIntStateOf(selectedTab) }
    val categories = listOf("Películas", "Series", "Documentales", "Mi Lista")
    val currentList by remember(selectedCategory) {
        mutableStateOf(
            when (selectedCategory) {
                0 -> movies
                1 -> series
                else -> emptyList()
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ── TOP BAR (MISMO estilo que RegisterActivity) ──────────────────────
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
                        text = "Your Watch History",
                        color = TextWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Track everything you've watched",
                        color = TextGray,
                        fontSize = 13.sp
                    )
                }
            }

            // ── CATEGORY TABS ───────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                categories.forEachIndexed { index, category ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            selectedCategory = index
                            onTabSelect(index)
                        }
                    ) {
                        Text(
                            text = category,
                            color = if (selectedCategory == index) RedMain else TextGray,
                            fontSize = 14.sp,
                            fontWeight = if (selectedCategory == index) FontWeight.Bold else FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(if (selectedCategory == index) 3.dp else 0.dp)
                                .background(RedMain, RoundedCornerShape(2.dp))
                        )
                    }
                }
            }

            // ── LISTA DE HISTORIAL ──────────────────────────────────────────────
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(currentList) { item ->
                    HistoryMovieCard(movie = item, onClick = { onMovieClick(item.title) })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun HistoryMovieCard(movie: WatchHistoryEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CardBg, RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFF2E2020), RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Poster placeholder (mismo estilo que las butacas)
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(RedDark, RedMain)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.History,
                contentDescription = null,
                tint = TextWhite.copy(alpha = 0.5f),
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Información de la película
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.title,
                color = TextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            if (movie.isCompleted) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = RedMain,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Completed",
                        color = TextGray,
                        fontSize = 12.sp
                    )
                }
            } else {
                Column {
                    LinearProgressIndicator(
                        progress = { movie.progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = RedMain,
                        trackColor = Color(0xFF3A3A3A)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "In progress: ${(movie.progress * 100).toInt()}%",
                        color = TextGray,
                        fontSize = 11.sp
                    )
                }
            }
        }

        if (movie.watchedAt > 0) {
            Text(
                text = "Watched recently",
                color = TextGray,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun HistorialScreenPreview() {
    CineScopeProyectTheme {
        HistorialScreen()
    }
}