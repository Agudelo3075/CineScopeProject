package com.desarrollox.cinescopeproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
                    // Aquí se cargaría la película según el título o ID
                    // Por ahora el ViewModel usa el título que recibe
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
        }
    }
}