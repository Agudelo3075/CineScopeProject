package com.desarrollox.cinescopeproyect.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.desarrollox.cinescopeproyect.data.remote.model.MovieBackend
import com.desarrollox.cinescopeproyect.ui.viewmodel.BackendTestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackendTestScreen(
    onBack: () -> Unit
) {
    val viewModel: BackendTestViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showAddDialog by remember { mutableStateOf(false) }
    var movieToEdit by remember { mutableStateOf<MovieBackend?>(null) }

    val bgDark = Color(0xFF120A0A)
    val redMain = Color(0xFFE50914)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Backend CRUD Test", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = bgDark)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = redMain,
                contentColor = Color.White
            ) {
                Text("+", fontSize = 24.sp, modifier = Modifier.padding(bottom = 2.dp))
            }
        },
        containerColor = bgDark
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(color = redMain, modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            uiState.error?.let {
                Text(text = it, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
            }

            uiState.successMessage?.let {
                Text(text = it, color = Color.Green, modifier = Modifier.padding(bottom = 8.dp))
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.movies) { movie ->
                    MovieItemCard(
                        movie = movie,
                        onEdit = { movieToEdit = movie },
                        onDelete = { movie.id?.let { viewModel.deleteMovie(it) } }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (showAddDialog) {
        MovieDialog(
            movie = null,
            onDismiss = { showAddDialog = false },
            onConfirm = { title, director, year ->
                viewModel.createMovie(title, director, year)
                showAddDialog = false
            }
        )
    }

    movieToEdit?.let { movie ->
        MovieDialog(
            movie = movie,
            onDismiss = { movieToEdit = null },
            onConfirm = { title, director, year ->
                movie.id?.let { viewModel.updateMovie(it, title, director, year) }
                movieToEdit = null
            }
        )
    }
}

@Composable
fun MovieItemCard(movie: MovieBackend, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1414)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("ID: ${movie.id}", color = Color.Gray, fontSize = 12.sp)
                Text(movie.title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(movie.director, color = Color(0xFFCCCCCC), fontSize = 14.sp)
                Text("Year: ${movie.year}", color = Color.Gray, fontSize = 12.sp)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDialog(
    movie: MovieBackend?,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int) -> Unit
) {
    var title by remember { mutableStateOf(movie?.title ?: "") }
    var director by remember { mutableStateOf(movie?.director ?: "") }
    var yearStr by remember { mutableStateOf(movie?.year?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (movie == null) "Add Movie" else "Edit Movie") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = director,
                    onValueChange = { director = it },
                    label = { Text("Director") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = yearStr,
                    onValueChange = { yearStr = it },
                    label = { Text("Year") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val year = yearStr.toIntOrNull() ?: 2024
                onConfirm(title, director, year)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
