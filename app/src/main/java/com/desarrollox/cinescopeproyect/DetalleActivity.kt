package com.desarrollox.cinescopeproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.desarrollox.cinescopeproyect.navigation.BottomRoute
import com.desarrollox.cinescopeproyect.navigation.CineScopeBottomBar
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme

private val BgMain = Color(0xFF120808)
private val RedAccent = Color(0xFFE50914)
private val TextWhite = Color(0xFFFFFFFF)
private val TextGrey = Color(0xFFB3B3B3)
private val CardDark = Color(0xFF1F1212)
private val SheetBg = Color(0xFF1A0D0D)

class DetallePelicula : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val titleExtra = intent.getStringExtra(EXTRA_TITLE).orEmpty()
        setContent {
            CineScopeProyectTheme {
                DetallePeliculaScreen(
                    detail = movieDetailForTitle(titleExtra),
                    onBack = { finish() }
                )
            }
        }
    }

    companion object {
        const val EXTRA_TITLE = "extra_movie_title"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetallePeliculaScreen(detail: MovieDetailUi, onBack: () -> Unit) {
    val context = LocalContext.current
    var showCalificar by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var seenOn by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMain)
    ) {
        Column(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp)
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = RedAccent
                        )
                    }
                    Text(
                        "Movie Details",
                        modifier = Modifier.weight(1f),
                        color = TextWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(40.dp)
                            .background(CardDark, CircleShape)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = RedAccent, modifier = Modifier.size(20.dp))
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(14.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(detail.color1, detail.color2)))
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .background(RedAccent, RoundedCornerShape(20.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, null, tint = TextWhite, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(detail.rating, color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    detail.title,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = TextWhite,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "${detail.year}  •  ${detail.genres}  •  ${detail.duration}",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = RedAccent,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DetailOutlineAction(Icons.Default.Star, "RATE", Modifier.weight(1f)) { showCalificar = true }
                    DetailOutlineAction(Icons.Default.Favorite, "FAV", Modifier.weight(1f)) { }
                    DetailOutlineAction(Icons.Default.Add, "LIST", Modifier.weight(1f)) { }
                    DetailSeenAction(seenOn, Modifier.weight(1f)) { seenOn = !seenOn }
                }

                Spacer(Modifier.height(28.dp))

                SectionTitleBar("Synopsis")
                Text(
                    detail.synopsis,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    color = TextGrey,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .width(3.dp)
                                .height(18.dp)
                                .background(RedAccent, RoundedCornerShape(2.dp))
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Cast", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        "View All",
                        color = RedAccent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { }
                    )
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(detail.cast) { member ->
                        CastItem(member)
                    }
                }

                Spacer(Modifier.height(24.dp))
            }

            CineScopeBottomBar(context = context, selected = BottomRoute.Inicio)
        }

        if (showCalificar) {
            ModalBottomSheet(
                onDismissRequest = { showCalificar = false },
                sheetState = sheetState,
                containerColor = SheetBg,
                dragHandle = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .width(40.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFF4A4A4A))
                        )
                    }
                }
            ) {
                CalificarSheetContent(
                    detail = detail,
                    onDismiss = { showCalificar = false },
                    onSave = { showCalificar = false }
                )
            }
        }
    }
}

@Composable
private fun DetailOutlineAction(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, RedAccent, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 4.dp)
    ) {
        Icon(icon, null, tint = RedAccent, modifier = Modifier.size(22.dp))
        Spacer(Modifier.height(6.dp))
        Text(label, color = TextWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DetailSeenAction(seenOn: Boolean, modifier: Modifier = Modifier, onToggle: () -> Unit) {
    val bg = if (seenOn) RedAccent else Color.Transparent
    val border = if (seenOn) RedAccent else RedAccent
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, border, RoundedCornerShape(10.dp))
            .background(bg)
            .clickable { onToggle() }
            .padding(vertical = 12.dp, horizontal = 4.dp)
    ) {
        Icon(
            Icons.Default.Visibility,
            null,
            tint = if (seenOn) TextWhite else RedAccent,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "SEEN",
            color = if (seenOn) TextWhite else RedAccent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SectionTitleBar(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Box(
            Modifier
                .width(3.dp)
                .height(18.dp)
                .background(RedAccent, RoundedCornerShape(2.dp))
        )
        Spacer(Modifier.width(8.dp))
        Text(title, color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun CastItem(member: CastMemberUi) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(72.dp)) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(member.avatar1, member.avatar2)))
        )
        Spacer(Modifier.height(8.dp))
        Text(
            member.actorLabel,
            color = TextWhite,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            member.characterName,
            color = RedAccent,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CalificarSheetContent(
    detail: MovieDetailUi,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    var stars by remember { mutableIntStateOf(4) }
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 28.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "CALIFICAR",
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, null, tint = TextGrey)
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF251616))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(52.dp)
                    .height(76.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Brush.verticalGradient(listOf(detail.color1, detail.color2)))
            )
            Column(modifier = Modifier.padding(start = 14.dp)) {
                Text(detail.title, color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(detail.director, color = RedAccent, fontSize = 13.sp)
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarToday, null, tint = TextGrey, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "${detail.year} • ${detail.genres}",
                        color = TextGrey,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(22.dp))

        Text(
            "TU PUNTUACIÓN",
            color = TextGrey,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp
        )
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..5) {
                val filled = i <= stars
                Icon(
                    imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (filled) RedAccent else Color(0xFF5C2A2A),
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { stars = i }
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            "%.1f".format(stars.toFloat()),
            modifier = Modifier.fillMaxWidth(),
            color = TextWhite,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(22.dp))

        Text(
            "COMENTARIO OPCIONAL",
            color = TextGrey,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            placeholder = {
                Text(
                    "¿Qué te pareció este viaje espacial?",
                    color = Color(0xFF5A5A5A),
                    fontSize = 14.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = RedAccent,
                unfocusedBorderColor = Color(0xFF3A2A2A),
                focusedContainerColor = Color(0xFF1A1010),
                unfocusedContainerColor = Color(0xFF1A1010),
                cursorColor = RedAccent
            ),
            shape = RoundedCornerShape(12.dp),
            maxLines = 5
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedAccent),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Save, null, tint = TextWhite, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(10.dp))
            Text("Guardar Calificación", color = TextWhite, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(12.dp))
        Text(
            "Cancelar",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onDismiss() }
                .padding(vertical = 8.dp),
            color = TextGrey,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🎬", fontSize = 12.sp)
            Spacer(Modifier.width(6.dp))
            Text(
                "CINESCOPE PREMIUM",
                color = TextGrey,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.6.sp
            )
        }
    }
}
