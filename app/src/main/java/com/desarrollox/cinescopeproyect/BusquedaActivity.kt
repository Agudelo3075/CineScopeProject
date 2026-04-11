package com.desarrollox.cinescopeproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.desarrollox.cinescopeproyect.navigation.BottomRoute
import com.desarrollox.cinescopeproyect.navigation.CineScopeBottomBar
import com.desarrollox.cinescopeproyect.navigation.navigateToMovieDetail
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme

private val BgMain = Color(0xFF120808)
private val ChipBg = Color(0xFF2A1515)
private val RedAccent = Color(0xFFE50914)
private val TextWhite = Color(0xFFFFFFFF)
private val TextGrey = Color(0xFFB3B3B3)
private val GoldStar = Color(0xFFFFC107)

private data class SearchResultItem(
    val title: String,
    val year: String,
    val genre: String,
    val rating: String,
    val color1: Color,
    val color2: Color
)

private val batmanResults = listOf(
    SearchResultItem("The Batman", "2022", "Action", "8.4", Color(0xFF1A0A14), Color(0xFF2E1520)),
    SearchResultItem("The Dark Knight", "2008", "Action", "9.0", Color(0xFF1A1A2E), Color(0xFF0D0D0D)),
    SearchResultItem("Batman Begins", "2005", "Action", "8.2", Color(0xFF0A1A10), Color(0xFF152520)),
    SearchResultItem("Batman Forever", "1995", "Action", "5.4", Color(0xFF201A30), Color(0xFF352040)),
    SearchResultItem("Batman v Superman", "2016", "Action", "6.5", Color(0xFF151820), Color(0xFF252A35)),
    SearchResultItem("The LEGO Batman Movie", "2017", "Animation", "7.3", Color(0xFF1A1520), Color(0xFF302040))
)

class Busqueda : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                BusquedaScreen(onBack = { finish() })
            }
        }
    }
}

@Composable
private fun BusquedaScreen(onBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var query by remember { mutableStateOf("Batman") }
    var chipIndex by remember { mutableIntStateOf(0) }
    val chips = listOf("Movies", "TV Shows", "Actors", "Directors")

    val filteredResults = remember(query, chipIndex) {
        val q = query.trim()
        val base = when (chipIndex) {
            0 -> batmanResults
            else -> batmanResults
        }
        if (q.isEmpty()) base
        else base.filter {
            it.title.contains(q, ignoreCase = true) ||
                it.genre.contains(q, ignoreCase = true)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMain)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = TextWhite
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(ChipBg)
                        .padding(horizontal = 14.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, null, tint = RedAccent, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(10.dp))
                        BasicTextField(
                            value = query,
                            onValueChange = { query = it },
                            textStyle = TextStyle(color = TextWhite, fontSize = 15.sp),
                            singleLine = true,
                            cursorBrush = SolidColor(RedAccent),
                            modifier = Modifier.weight(1f)
                        )
                        if (query.isNotEmpty()) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = null,
                                tint = RedAccent,
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable { query = "" }
                            )
                        }
                    }
                }
                Spacer(Modifier.width(8.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                chips.forEachIndexed { index, label ->
                    val selected = chipIndex == index
                    Text(
                        text = label,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (selected) RedAccent else ChipBg)
                            .clickable { chipIndex = index }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        color = TextWhite,
                        fontSize = 13.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Results for '$query'",
                    color = TextWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${filteredResults.size} results",
                    color = RedAccent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (filteredResults.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Sin resultados para \"$query\"",
                        color = TextGrey,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 88.dp)
                ) {
                    items(filteredResults, key = { it.title }) { item ->
                        SearchGridCell(item) { context.navigateToMovieDetail(item.title) }
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            CineScopeBottomBar(context = context, selected = BottomRoute.Inicio)
        }
    }
}

@Composable
private fun SearchGridCell(item: SearchResultItem, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable { onClick() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.verticalGradient(listOf(item.color1, item.color2)))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color(0xCC000000), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = GoldStar, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(item.rating, color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(
            item.title,
            color = TextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(4.dp))
        Text(
            "${item.year} • ${item.genre}",
            color = TextGrey,
            fontSize = 12.sp
        )
    }
}
