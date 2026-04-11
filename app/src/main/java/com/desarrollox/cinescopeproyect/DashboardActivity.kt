package com.desarrollox.cinescopeproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.desarrollox.cinescopeproyect.navigation.BottomRoute
import com.desarrollox.cinescopeproyect.navigation.CineScopeBottomBar
import com.desarrollox.cinescopeproyect.navigation.navigateToBusqueda
import com.desarrollox.cinescopeproyect.navigation.navigateToMovieDetail
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme

// ─── Colores ──────────────────────────────────────────────────────────────────
private val BgDark       = Color(0xFF0D0D0D)
private val CardDark     = Color(0xFF1A1A1A)
private val RedMain      = Color(0xFFE53935)
private val RedDark      = Color(0xFFB71C1C)
private val TextWhite    = Color(0xFFF5F5F5)
private val TextGray     = Color(0xFF9E9E9E)
// ─── Datos de películas ───────────────────────────────────────────────────────
data class Movie(
    val title: String,
    val genre: String,
    val duration: String,
    val color1: Color,
    val color2: Color,
    val isNew: Boolean = false
)

val popularMovies = listOf(
    Movie("The Dark Knight",   "Action, Drama",  "2h 32m", Color(0xFF1A1A2E), Color(0xFF16213E)),
    Movie("Inception",         "Sci-Fi, Thriller","2h 28m", Color(0xFF2D1B00), Color(0xFF4A2F00)),
    Movie("The Godfather",     "Crime, Drama",   "2h 55m", Color(0xFF1A0A00), Color(0xFF2E1500)),
    Movie("Pulp Fiction",      "Crime, Drama",   "2h 34m", Color(0xFF0A1A00), Color(0xFF152E00)),
)

val topRatedMovies = listOf(
    Movie("Schindler's List",  "Drama, History", "3h 15m", Color(0xFF1A1A1A), Color(0xFF2E2E2E)),
    Movie("Pulp Fiction",      "Crime, Drama",   "2h 34m", Color(0xFF001A0A), Color(0xFF002E15)),
    Movie("Forrest Gump",      "Drama, Romance", "2h 22m", Color(0xFF001A1A), Color(0xFF002E2E)),
    Movie("Fight Club",        "Drama, Thriller","2h 19m", Color(0xFF1A001A), Color(0xFF2E002E)),
)

val newReleases = listOf(
    Movie("Supernova",         "Sci-Fi, Action", "2h 10m", Color(0xFF0A0020), Color(0xFF200040), isNew = true),
    Movie("The Last Horizon",  "Drama, Mystery", "1h 58m", Color(0xFF001020), Color(0xFF002040), isNew = true),
    Movie("Crimson Peak",      "Horror, Drama",  "1h 59m", Color(0xFF200000), Color(0xFF400010), isNew = true),
    Movie("Neon Nights",       "Thriller, Sci-Fi","2h 5m", Color(0xFF001A10), Color(0xFF003020), isNew = true),
)

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                DasboardScreen()
            }
        }
    }
}

@Composable
fun DasboardScreen() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().background(BgDark)) {

        // ── Contenido principal con scroll ────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 72.dp)
        ) {

            // ── TOP BAR ───────────────────────────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Logo
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(RedMain, RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) { Text("🎬", fontSize = 14.sp) }
                Spacer(Modifier.width(8.dp))
                Text(
                    "CineScope",
                    color = TextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                // Buscador
                Box(
                    modifier = Modifier
                        .background(CardDark, RoundedCornerShape(20.dp))
                        .clickable { context.navigateToBusqueda() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Search, null, tint = TextGray, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Search mov...", color = TextGray, fontSize = 12.sp)
                    }
                }
                Spacer(Modifier.width(10.dp))
                // Avatar
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(RedMain, CircleShape),
                    contentAlignment = Alignment.Center
                ) { Text("U", color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold) }
            }

            // ── FEATURED HERO ─────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                // Fondo estilo espacio/interestelar
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF000510),
                                    Color(0xFF001030),
                                    Color(0xFF002050),
                                    Color(0xFF001530),
                                    Color(0xFF000510)
                                )
                            )
                        )
                )
                // Estrellas simuladas (puntos blancos)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                listOf(Color(0x33FFFFFF), Color(0x00000000)),
                                radius = 400f
                            )
                        )
                )
                // Planeta/agujero negro simulado
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.TopEnd)
                        .padding(top = 20.dp, end = 20.dp)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    Color(0xFF1A3A6A),
                                    Color(0xFF0A1A40),
                                    Color(0xFF000510)
                                )
                            ),
                            CircleShape
                        )
                )
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.TopEnd)
                        .padding(top = 40.dp, end = 40.dp)
                        .background(
                            Brush.radialGradient(listOf(Color(0xFF2A5AAA), Color(0xFF0A1A40))),
                            CircleShape
                        )
                )
                // Degradado inferior
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .align(Alignment.BottomCenter)
                        .background(Brush.verticalGradient(listOf(Color.Transparent, BgDark)))
                )
                // Contenido del hero
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp)
                ) {
                    // Badge TRENDING NOW
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(RedMain, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                "▶ TRENDING NOW",
                                color = TextWhite,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Science Fiction • Action",
                            color = TextGray,
                            fontSize = 10.sp
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "INTERSTELLAR",
                        color = TextWhite,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "A team of explorers travel through a wormhole\nin space in an attempt to ensure humanity's...",
                        color = TextGray,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                    Spacer(Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Watch Now
                        Box(
                            modifier = Modifier
                                .background(
                                    Brush.horizontalGradient(listOf(RedDark, RedMain)),
                                    RoundedCornerShape(24.dp)
                                )
                                .clickable { context.navigateToMovieDetail("Interstellar") }
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("▶", color = TextWhite, fontSize = 12.sp)
                                Spacer(Modifier.width(6.dp))
                                Text("Watch Now", color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        // My List
                        Box(
                            modifier = Modifier
                                .background(Color(0x33FFFFFF), RoundedCornerShape(24.dp))
                                .clickable { }
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("+", color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.width(6.dp))
                                Text("My List", color = TextWhite, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // ── POPULAR ───────────────────────────────────────────────────────
            SectionHeader("Popular", "View All")
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                popularMovies.forEach { movie ->
                    MovieCard(movie, width = 130, height = 190) {
                        context.navigateToMovieDetail(movie.title)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── TOP RATED ─────────────────────────────────────────────────────
            SectionHeader("Top Rated", "View All")
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                topRatedMovies.forEach { movie ->
                    MovieCard(movie, width = 130, height = 190) {
                        context.navigateToMovieDetail(movie.title)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── NEW RELEASES ──────────────────────────────────────────────────
            SectionHeader("New Releases", "View All")
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                newReleases.forEach { movie ->
                    MovieCard(movie, width = 130, height = 190) {
                        context.navigateToMovieDetail(movie.title)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }

        // ── BOTTOM NAVIGATION ─────────────────────────────────────────────────
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            CineScopeBottomBar(context = context, selected = BottomRoute.Inicio)
        }
    }
}

// ─── Componentes reutilizables ────────────────────────────────────────────────

@Composable
fun SectionHeader(title: String, action: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(18.dp)
                    .background(RedMain, RoundedCornerShape(2.dp))
            )
            Spacer(Modifier.width(8.dp))
            Text(title, color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        Text(
            action,
            color = RedMain,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable { }
        )
    }
}

@Composable
fun MovieCard(movie: Movie, width: Int, height: Int, onClick: () -> Unit = {}) {
    Column(modifier = Modifier.width(width.dp)) {
        Box(
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Brush.verticalGradient(listOf(movie.color1, movie.color2)))
                .clickable { onClick() }
        ) {
            // Textura/detalle interior
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            listOf(Color(0x22FFFFFF), Color(0x00000000)),
                            radius = 200f
                        )
                    )
            )
            // Badge NEW
            if (movie.isNew) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                        .background(RedMain, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("NEW", color = TextWhite, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                }
            }
            // Título sobre la card
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC000000)))
                    )
                    .padding(8.dp)
            ) {
                Text(
                    movie.title,
                    color = TextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    movie.genre,
                    color = TextGray,
                    fontSize = 9.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun HomePreview() {
    CineScopeProyectTheme {
        DasboardScreen()
    }
}