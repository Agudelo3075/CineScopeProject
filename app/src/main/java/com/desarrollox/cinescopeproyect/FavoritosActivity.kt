package com.desarrollox.cinescopeproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme

// ─── Colores (MISMOS que RegisterActivity) ────────────────────────────────────
private val BgDark      = Color(0xFF120A0A)
private val RedMain     = Color(0xFFE53935)
private val RedDark     = Color(0xFFB71C1C)
private val TextWhite   = Color(0xFFF5F5F5)
private val TextGray    = Color(0xFF9E8E8E)
private val CardBg      = Color(0xFF1E1414)

// ─── Datos de ejemplo para favoritos ──────────────────────────────────────────
private data class FavoriteMovie(
    val title: String,
    val year: String,
    val rating: String,
    val gradientStart: Color = Color(0xFF2D1B00),
    val gradientEnd: Color = Color(0xFF1A0A2E)
)

private val favoriteMovies = listOf(
    FavoriteMovie("Inception", "2010", "8.8", Color(0xFF2D1B00), Color(0xFF1A0A2E)),
    FavoriteMovie("The Dark Knight", "2008", "9.0", Color(0xFF1A1A2E), Color(0xFF0D0D0D)),
    FavoriteMovie("Interstellar", "2014", "8.7", Color(0xFF000510), Color(0xFF001530)),
    FavoriteMovie("Dune: Part Two", "2024", "8.9", Color(0xFF1A1500), Color(0xFF2A2000)),
    FavoriteMovie("Spider-Verse", "2023", "9.1", Color(0xFF1A0030), Color(0xFF300040)),
    FavoriteMovie("Blade Runner 2049", "2017", "8.0", Color(0xFF0A1520), Color(0xFF152535)),
    FavoriteMovie("Oppenheimer", "2023", "8.5", Color(0xFF2A0A0A), Color(0xFF3A1515)),
    FavoriteMovie("Parasite", "2019", "8.6", Color(0xFF0A1A0A), Color(0xFF0F2A0F))
)

private val favoriteSeries = listOf(
    FavoriteMovie("Stranger Things", "2016", "8.7", Color(0xFF1A0A2E), Color(0xFF2D1B00)),
    FavoriteMovie("The Crown", "2016", "8.6", Color(0xFF2A0A1A), Color(0xFF3A1525)),
    FavoriteMovie("Breaking Bad", "2008", "9.5", Color(0xFF0A2A1A), Color(0xFF0F3A25)),
    FavoriteMovie("The Last of Us", "2023", "8.8", Color(0xFF1A2A0A), Color(0xFF253A0F))
)

class FavoritosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                FavoritosScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun FavoritosScreen(
    onBack: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Películas", "Series")

    // Mostrar contenido según la pestaña seleccionada
    val currentList = if (selectedTab == 0) favoriteMovies else favoriteSeries

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
                        text = "Your Favorites",
                        color = TextWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Movies and series you love",
                        color = TextGray,
                        fontSize = 13.sp
                    )
                }
            }

            // ── TABS (Mismo estilo que RegisterActivity) ─────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                tabs.forEachIndexed { index, tab ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { selectedTab = index }
                    ) {
                        Text(
                            text = tab,
                            color = if (selectedTab == index) RedMain else TextGray,
                            fontSize = 15.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(if (selectedTab == index) 3.dp else 0.dp)
                                .background(RedMain, RoundedCornerShape(2.dp))
                        )
                    }
                }
            }

            // ── GRID DE FAVORITOS ────────────────────────────────────────────────
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 88.dp, top = 8.dp)
            ) {
                items(currentList) { movie ->
                    FavoriteMovieCard(movie)
                }
            }
        }
    }
}

@Composable
private fun FavoriteMovieCard(movie: FavoriteMovie) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(movie.gradientStart, movie.gradientEnd)
                )
            )
    ) {
        // Icono de favorito en la esquina
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .size(36.dp)
                .background(Color(0x66000000), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                tint = RedMain,
                modifier = Modifier.size(18.dp)
            )
        }

        // Degradado inferior para el texto
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC000000))
                    )
                )
                .padding(12.dp)
        ) {
            Text(
                text = movie.title,
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Rating con estrella
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = RedMain,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.rating,
                        color = TextWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Año
                Text(
                    text = movie.year,
                    color = TextGray,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun FavoritosScreenPreview() {
    CineScopeProyectTheme {
        FavoritosScreen()
    }
}