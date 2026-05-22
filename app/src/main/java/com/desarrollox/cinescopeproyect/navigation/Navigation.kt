package com.desarrollox.cinescopeproyect.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.desarrollox.cinescopeproyect.Busqueda
import com.desarrollox.cinescopeproyect.DashboardActivity
import com.desarrollox.cinescopeproyect.DetallePelicula
import com.desarrollox.cinescopeproyect.FavoritosActivity
import com.desarrollox.cinescopeproyect.HistorialActivity
import com.desarrollox.cinescopeproyect.MiListaActivity
import com.desarrollox.cinescopeproyect.PerfilActivity

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
    object BackendTest : Screen("backend_test")
}

/** Destinos de la barra inferior principal. */
enum class BottomRoute(val route: String) {
    Inicio(Screen.Dashboard.route),
    Historial(Screen.Historial.route),
    MiLista(Screen.MiLista.route),
    Favoritos(Screen.Favoritos.route),
    Perfil(Screen.Perfil.route)
}

fun detallePeliculaIntent(context: Context, movieTitle: String): Intent =
    Intent(context, DetallePelicula::class.java).putExtra(DetallePelicula.EXTRA_TITLE, movieTitle)

fun busquedaIntent(context: Context): Intent = Intent(context, Busqueda::class.java)

private fun intentForSecondary(context: Context, target: BottomRoute): Intent = when (target) {
    BottomRoute.Inicio -> Intent(context, DashboardActivity::class.java)
    BottomRoute.Historial -> Intent(context, HistorialActivity::class.java)
    BottomRoute.MiLista -> Intent(context, MiListaActivity::class.java)
    BottomRoute.Favoritos -> Intent(context, FavoritosActivity::class.java)
    BottomRoute.Perfil -> Intent(context, PerfilActivity::class.java)
}

fun Context.navigateToMovieDetail(movieTitle: String) {
    startActivity(detallePeliculaIntent(this, movieTitle))
}

fun Context.navigateToBusqueda() {
    startActivity(busquedaIntent(this))
}

fun Context.navigateToBottomRoute(target: BottomRoute) {
    val activity = this as? Activity ?: return
    val isSameDestination = when {
        target == BottomRoute.Inicio && activity is DashboardActivity -> true
        target == BottomRoute.Historial && activity is HistorialActivity -> true
        target == BottomRoute.MiLista && activity is MiListaActivity -> true
        target == BottomRoute.Favoritos && activity is FavoritosActivity -> true
        target == BottomRoute.Perfil && activity is PerfilActivity -> true
        else -> false
    }
    if (isSameDestination) return

    if (target == BottomRoute.Inicio) {
        activity.startActivity(
            Intent(activity, DashboardActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
        )
        return
    }

    activity.startActivity(intentForSecondary(activity, target))
    if (activity !is DashboardActivity) {
        activity.finish()
    }
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
