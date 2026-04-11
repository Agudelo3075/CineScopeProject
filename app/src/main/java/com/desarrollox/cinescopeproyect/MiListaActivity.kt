package com.desarrollox.cinescopeproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme

// ─── Colores (MISMOS que RegisterActivity) ────────────────────────────────────
private val BgDark      = Color(0xFF120A0A)
private val RedMain     = Color(0xFFE53935)
private val TextWhite   = Color(0xFFF5F5F5)
private val TextGray    = Color(0xFF9E8E8E)
private val MatchGreen  = Color(0xFF4CAF50)

// ─── Datos de ejemplo para Mi Lista ──────────────────────────────────────────
private data class ListaItem(
    val title: String,
    val match: String,
    val genres: String,
    val added: String,
    val color1: Color,
    val color2: Color
)

private val miListaData = listOf(
    ListaItem("Inception", "95%", "Sci-Fi, Acción", "Añadido hace 2 días", Color(0xFF2D1B00), Color(0xFF1A0A2E)),
    ListaItem("The Dark Knight", "98%", "Acción, Drama", "Añadido ayer", Color(0xFF1A1A2E), Color(0xFF0D0D0D)),
    ListaItem("Interstellar", "92%", "Sci-Fi, Aventura", "Añadido hace 1 semana", Color(0xFF000510), Color(0xFF001530)),
    ListaItem("Dune: Part Two", "99%", "Épico, Sci-Fi", "Añadido hoy", Color(0xFF1A1500), Color(0xFF2A2000)),
    ListaItem("Parasite", "89%", "Thriller, Drama", "Añadido hace 2 semanas", Color(0xFF0A1A0A), Color(0xFF152E15)),
    ListaItem("Oppenheimer", "97%", "Drama, Historia", "Añadido hace 3 días", Color(0xFF2A0A0A), Color(0xFF3A1515)),
    ListaItem("Spider-Verse", "96%", "Animación, Acción", "Añadido ayer", Color(0xFF1A0030), Color(0xFF300040))
)

class MiListaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                MiListaScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
fun MiListaScreen(
    onBack: () -> Unit = {}
) {
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

                // Botón de búsqueda
                Text(
                    text = "🔍",
                    color = TextWhite,
                    fontSize = 20.sp,
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

                // Butacas
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
                        text = "My Watchlist",
                        color = TextWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Movies you want to watch",
                        color = TextGray,
                        fontSize = 13.sp
                    )
                }
            }

            // ── CONTADOR DE PELÍCULAS ───────────────────────────────────────────
            Text(
                text = "${miListaData.size} PELÍCULAS GUARDADAS",
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                color = RedMain,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )

            // ── LISTA DE PELÍCULAS ──────────────────────────────────────────────
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentPadding = PaddingValues(bottom = 88.dp, top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(miListaData) { item ->
                    ListaMovieCard(item)
                }
            }
        }
    }
}

@Composable
private fun ListaMovieCard(item: ListaItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1414), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Poster con gradiente
        Box(
            modifier = Modifier
                .width(72.dp)
                .height(104.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(item.color1, item.color2)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("🎬", fontSize = 28.sp, color = TextWhite.copy(alpha = 0.5f))
        }

        // Información de la película
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = item.title,
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Match y géneros
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "${item.match} Match",
                    color = MatchGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "•",
                    color = TextGray,
                    fontSize = 12.sp
                )
                Text(
                    text = item.genres,
                    color = TextGray,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.added,
                color = TextGray.copy(alpha = 0.7f),
                fontSize = 11.sp
            )
        }

        // Botón de eliminar
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFF2A2020), CircleShape)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🗑️",
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun MiListaScreenPreview() {
    CineScopeProyectTheme {
        MiListaScreen()
    }
}