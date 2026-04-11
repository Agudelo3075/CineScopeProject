package com.desarrollox.cinescopeproyect

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme
import kotlinx.coroutines.delay

// ─── Colores del tema ────────────────────────────────────────────────────────
private val BackgroundDark  = Color(0xFF0A0A0A)
private val DeepRed         = Color(0xFF8B0000)
private val BrightRed       = Color(0xFFCC1A1A)
private val AccentRed       = Color(0xFFE53935)
private val TextWhite       = Color(0xFFEEEEEE)
private val TextGray        = Color(0xFF9E9E9E)
private val ProgressBg      = Color(0xFF2A2A2A)

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                SplashScreen(
                    onFinished = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun SplashScreen(onFinished: () -> Unit = {}) {

    // ── Progreso animado 0→100 en ~3 segundos ────────────────────────────────
    var progress by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 2800, easing = LinearEasing),
        label = "progress"
    )

    // ── Fade-in del logo ──────────────────────────────────────────────────────
    var logoAlpha by remember { mutableFloatStateOf(0f) }
    val animatedLogoAlpha by animateFloatAsState(
        targetValue = logoAlpha,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "logoAlpha"
    )

    LaunchedEffect(Unit) {
        logoAlpha = 1f
        delay(300)
        progress = 1f
        delay(3200)
        onFinished()
    }

    // ── Fondo degradado radial rojo→negro ─────────────────────────────────────
    val bgBrush = Brush.radialGradient(
        colors = listOf(DeepRed, BackgroundDark),
        center = Offset(0.5f, 0.38f),   // centro un poco arriba del medio
        radius = 900f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush),
        contentAlignment = Alignment.Center
    ) {

        // ── Contenido central ────────────────────────────────────────────────
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(animatedLogoAlpha)
        ) {

            // Icono claqueta (emoji como placeholder; reemplaza con tu ImageVector/Painter)
            Text(
                text = "🎬",
                fontSize = 56.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // CINESCOPE
            Text(
                text = "CINESCOPE",
                color = TextWhite,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 6.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Subtítulo
            Text(
                text = "CINEMATIC EXCELLENCE",
                color = AccentRed,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center
            )
        }

        // ── Barra de progreso + texto abajo ──────────────────────────────────
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
                .padding(horizontal = 40.dp)
        ) {
            // Texto de estado
            Text(
                text = "Initializing CineScope experience...",
                color = TextGray,
                fontSize = 11.sp,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            // Track de la barra
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .clip(RoundedCornerShape(50))
                    .background(ProgressBg)
            ) {
                // Relleno animado
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50))
                        .background(
                            Brush.horizontalGradient(
                                listOf(BrightRed, AccentRed)
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Porcentaje
            Text(
                text = "${(animatedProgress * 100).toInt()}%",
                color = TextGray,
                fontSize = 10.sp,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Pie de pantalla
            Text(
                text = "POWERED BY CINESCOPE",
                color = Color(0xFF555555),
                fontSize = 9.sp,
                letterSpacing = 2.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun SplashPreview() {
    CineScopeProyectTheme {
        SplashScreen()
    }
}