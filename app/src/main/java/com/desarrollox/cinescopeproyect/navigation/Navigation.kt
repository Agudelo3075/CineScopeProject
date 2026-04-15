package com.desarrollox.cinescopeproyect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.desarrollox.cinescopeproyect.*

/** Rutas de navegación de la aplicación. */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object Busqueda : Screen("busqueda")
    object Detalle : Screen("detalle/{title}") {
        fun createRoute(title: String) = "detalle/$title"
    }
    object Historial : Screen("historial")
    object MiLista : Screen("mi_lista")
    object Favoritos : Screen("favoritos")
    object Perfil : Screen("perfil")
}

/** Destinos de la barra inferior principal. */
enum class BottomRoute(val route: String) {
    Inicio(Screen.Dashboard.route),
    Historial(Screen.Historial.route),
    MiLista(Screen.MiLista.route),
    Favoritos(Screen.Favoritos.route),
    Perfil(Screen.Perfil.route)
}

@Composable
fun CineScopeNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            // Se llamará desde MainActivity
        }
        composable(Screen.Register.route) {
            // Se llamará desde MainActivity
        }
        composable(Screen.Dashboard.route) {
            // Contenido del Dashboard
        }
        composable(Screen.Busqueda.route) {
            // Contenido de Búsqueda
        }
        composable(
            route = Screen.Detalle.route,
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            // Contenido de Detalle
        }
        composable(Screen.Historial.route) {
            // Contenido de Historial
        }
        composable(Screen.MiLista.route) {
            // Contenido de Mi Lista
        }
        composable(Screen.Favoritos.route) {
            // Contenido de Favoritos
        }
        composable(Screen.Perfil.route) {
            // Contenido de Perfil
        }
    }
}
