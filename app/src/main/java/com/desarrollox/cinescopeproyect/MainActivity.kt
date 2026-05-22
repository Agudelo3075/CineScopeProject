package com.desarrollox.cinescopeproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.desarrollox.cinescopeproyect.navigation.CineScopeBottomBar
import com.desarrollox.cinescopeproyect.navigation.Screen
import com.desarrollox.cinescopeproyect.ui.theme.CineScopeProyectTheme
import com.desarrollox.cinescopeproyect.ui.viewmodel.*
import com.desarrollox.cinescopeproyect.ui.BackendTestScreen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineScopeProyectTheme {
                CineScopeApp()
            }
        }
    }
}

@Composable
fun CineScopeApp() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = { CineScopeBottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // LOGIN
            composable(Screen.Login.route) {
                val viewModel: AuthViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                LaunchedEffect(uiState.loginSuccess) {
                    if (uiState.loginSuccess) {
                        viewModel.resetLoginSuccess()
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                }
                
                MainLoginScreen(
                    isLoading = uiState.isLoading,
                    error = uiState.error,
                    onLogin = { e, p -> viewModel.login(e, p) },
                    onGoToRegister = { navController.navigate(Screen.Register.route) },
                    onClearError = { viewModel.clearError() }
                )
            }

            // REGISTER
            composable(Screen.Register.route) {
                val viewModel: AuthViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                LaunchedEffect(uiState.registerSuccess) {
                    if (uiState.registerSuccess) {
                        viewModel.resetRegisterSuccess()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    }
                }
                
                RegisterScreen(
                    isLoading = uiState.isLoading,
                    error = uiState.error,
                    onRegister = { n, e, p, c -> viewModel.register(n, e, p, c) },
                    onGoToLogin = { navController.popBackStack() },
                    onClearError = { viewModel.clearError() }
                )
            }

            // DASHBOARD
            composable(Screen.Dashboard.route) {
                val viewModel: HomeViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                DasboardScreen(
                    isLoading = uiState.isLoading,
                    popularMovies = uiState.popularMovies,
                    topRatedMovies = uiState.topRatedMovies,
                    newReleases = uiState.newReleases,
                    onNavigateToBusqueda = { navController.navigate(Screen.Busqueda.route) },
                    onNavigateToMovieDetail = { title -> 
                        navController.navigate(Screen.Detalle.createRoute(title))
                    },
                    onTestBackendClick = {
                        navController.navigate(Screen.BackendTest.route)
                    }
                )
            }

            // BUSQUEDA
            composable(Screen.Busqueda.route) {
                val viewModel: SearchViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                BusquedaScreen(
                    query = uiState.query,
                    selectedChip = uiState.selectedChip,
                    results = uiState.results,
                    isSearching = uiState.isSearching,
                    hasSearched = uiState.hasSearched,
                    onQueryChange = { viewModel.updateQuery(it) },
                    onChipSelect = { viewModel.selectChip(it) },
                    onBack = { navController.popBackStack() },
                    onMovieClick = { title ->
                        navController.navigate(Screen.Detalle.createRoute(title))
                    }
                )
            }

            // DETALLE
            composable(
                route = Screen.Detalle.route,
                arguments = listOf(navArgument("title") { type = NavType.StringType })
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val viewModel: MovieDetailViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                LaunchedEffect(title) {
                    viewModel.loadMovieByTitle(title)
                }
                
                DetallePeliculaScreen(
                    detail = movieDetailForTitle(title),
                    movie = uiState.movie,
                    isFavorite = uiState.isFavorite,
                    isInMyList = uiState.isInMyList,
                    isInHistory = uiState.isInHistory,
                    userRating = uiState.userRating,
                    onToggleFavorite = { viewModel.toggleFavorite() },
                    onToggleMyList = { viewModel.toggleMyList() },
                    onMarkAsWatched = { viewModel.markAsWatched() },
                    onRate = { stars, comment -> viewModel.rateMovie(stars, comment) },
                    onBack = { navController.popBackStack() }
                )
            }

            // HISTORIAL
            composable(Screen.Historial.route) {
                val viewModel: HistoryViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                HistorialScreen(
                    movies = uiState.movies,
                    series = uiState.series,
                    selectedTab = uiState.selectedTab,
                    isLoading = uiState.isLoading,
                    onTabSelect = { viewModel.selectTab(it) },
                    onBack = { navController.popBackStack() },
                    onMovieClick = { title ->
                        navController.navigate(Screen.Detalle.createRoute(title))
                    }
                )
            }

            // MI LISTA
            composable(Screen.MiLista.route) {
                val viewModel: MyListViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                MiListaScreen(
                    items = uiState.items,
                    itemCount = uiState.itemCount,
                    isLoading = uiState.isLoading,
                    onRemoveItem = { viewModel.removeFromList(it) },
                    onBack = { navController.popBackStack() },
                    onMovieClick = { title ->
                        navController.navigate(Screen.Detalle.createRoute(title))
                    }
                )
            }

            // FAVORITOS
            composable(Screen.Favoritos.route) {
                val viewModel: FavoritesViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                FavoritosScreen(
                    movies = uiState.movies,
                    series = uiState.series,
                    selectedTab = uiState.selectedTab,
                    isLoading = uiState.isLoading,
                    onTabSelect = { viewModel.selectTab(it) },
                    onRemoveFavorite = { viewModel.removeFavorite(it) },
                    onBack = { navController.popBackStack() },
                    onMovieClick = { title ->
                        navController.navigate(Screen.Detalle.createRoute(title))
                    }
                )
            }

            // PERFIL
            composable(Screen.Perfil.route) {
                val viewModel: ProfileViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                
                LaunchedEffect(uiState.logoutSuccess) {
                    if (uiState.logoutSuccess) {
                        viewModel.resetLogoutSuccess()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
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
                    onBack = { navController.popBackStack() }
                )
            }

            // BACKEND TEST
            composable(Screen.BackendTest.route) {
                BackendTestScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

// ─── LOGIN SCREEN (NEW STYLE) ──────────────────────────────────────────────
@Composable
fun MainLoginScreen(
    isLoading: Boolean = false,
    error: String? = null,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onGoToRegister: () -> Unit = {},
    onClearError: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val bgDark = Color(0xFF120A0A)
    val redMain = Color(0xFFE50914)
    val textGray = Color(0xFF9E8E8E)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            // Header: Logo and Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 32.dp, 24.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(redMain, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎬", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "CineScope",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Cinema Header Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 24.dp)
                    .background(Color(0xFF2A0808), RoundedCornerShape(16.dp))
            ) {
                // Decorative Cinema Background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF3D0A0A), Color(0xFF120A0A))
                            )
                        )
                )
                
                // Light beam effect
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0x33FFFFFF), Color.Transparent),
                                radius = 400f
                            )
                        )
                )

                // Seats simulation
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                ) {
                    repeat(3) { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = (row * 10).dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(8) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp, 12.dp)
                                        .padding(2.dp)
                                        .background(
                                            Color(0xFF8B0000).copy(alpha = 0.6f + row * 0.1f),
                                            RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                        )
                                )
                            }
                        }
                    }
                }

                // Text overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Welcome Back",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Ready for your next premiere?",
                        color = textGray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form Fields
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                LoginFieldLabel("EMAIL ADDRESS")
                LoginTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        if (error != null) onClearError()
                    },
                    placeholder = "name@example.com",
                    icon = Icons.Default.Email,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    LoginFieldLabel("PASSWORD")
                    Text(
                        text = "Forgot?",
                        color = redMain,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .clickable { /* action */ }
                    )
                }
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        if (error != null) onClearError()
                    },
                    placeholder = { Text("Enter your password", color = textGray.copy(0.5f)) },
                    leadingIcon = { Icon(Icons.Default.Lock, null, tint = textGray) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                null, tint = textGray
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = loginFieldColors(),
                    enabled = !isLoading,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { rememberMe = !rememberMe }
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = redMain,
                            uncheckedColor = textGray,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        text = "Remember this device",
                        color = textGray,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (!error.isNullOrBlank()) {
                    Text(
                        text = error,
                        color = redMain,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Button(
                    onClick = { onLogin(email.trim(), password) },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = redMain)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isLoading) "Signing In..." else "Sign In",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.ArrowForward, null, modifier = Modifier.size(20.dp), tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Divider(color = textGray.copy(alpha = 0.2f), thickness = 1.dp)
                    Text(
                        text = "OR",
                        color = textGray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(bgDark)
                            .padding(horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SocialButton(
                        text = "Google",
                        icon = "Aa", 
                        modifier = Modifier.weight(1f)
                    )
                    SocialButton(
                        text = "Apple",
                        icon = "", 
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("New to CineScope? ", color = textGray, fontSize = 14.sp)
                    Text(
                        text = "Create an account",
                        color = redMain,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onGoToRegister() }
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginFieldLabel(text: String) {
    Text(
        text = text,
        color = Color(0xFFCCCCCC),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    enabled: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color(0xFF9E8E8E).copy(0.5f)) },
        leadingIcon = { Icon(icon, null, tint = Color(0xFF9E8E8E)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = loginFieldColors(),
        enabled = enabled,
        singleLine = true
    )
}

@Composable
private fun SocialButton(text: String, icon: String, modifier: Modifier) {
    Card(
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1414)),
        border = BorderStroke(1.dp, Color(0xFF2E2020))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFFE50914),
    unfocusedBorderColor = Color(0xFF2E2020),
    focusedContainerColor = Color(0xFF1A1010),
    unfocusedContainerColor = Color(0xFF1A1010),
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    cursorColor = Color(0xFFE50914)
)