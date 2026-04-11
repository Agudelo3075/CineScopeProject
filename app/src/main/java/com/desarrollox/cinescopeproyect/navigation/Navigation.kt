package com.desarrollox.cinescopeproyect.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.desarrollox.cinescopeproyect.Busqueda
import com.desarrollox.cinescopeproyect.DashboardActivity
import com.desarrollox.cinescopeproyect.DetallePelicula
import com.desarrollox.cinescopeproyect.FavoritosActivity
import com.desarrollox.cinescopeproyect.HistorialActivity
import com.desarrollox.cinescopeproyect.MiListaActivity
import com.desarrollox.cinescopeproyect.PerfilActivity

/** Destinos de la barra inferior principal. */
enum class BottomRoute {
    Inicio,
    Historial,
    MiLista,
    Favoritos,
    Perfil
}

// ─── Intents reutilizables ───────────────────────────────────────────────────

fun dashboardIntent(context: Context): Intent =
    Intent(context, DashboardActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    }

fun busquedaIntent(context: Context): Intent =
    Intent(context, Busqueda::class.java)

fun detallePeliculaIntent(context: Context, movieTitle: String): Intent =
    Intent(context, DetallePelicula::class.java).putExtra(
        DetallePelicula.EXTRA_TITLE,
        movieTitle
    )

fun historialIntent(context: Context): Intent =
    Intent(context, HistorialActivity::class.java)

fun miListaIntent(context: Context): Intent =
    Intent(context, MiListaActivity::class.java)

fun favoritosIntent(context: Context): Intent =
    Intent(context, FavoritosActivity::class.java)

fun perfilIntent(context: Context): Intent =
    Intent(context, PerfilActivity::class.java)

private fun secondaryIntent(context: Context, target: BottomRoute): Intent = when (target) {
    BottomRoute.Inicio -> Intent(context, DashboardActivity::class.java)
    BottomRoute.Historial -> historialIntent(context)
    BottomRoute.MiLista -> miListaIntent(context)
    BottomRoute.Favoritos -> favoritosIntent(context)
    BottomRoute.Perfil -> perfilIntent(context)
}

// ─── Navegación desde Context (activities secundarias) ───────────────────────

/** Abre la pantalla de búsqueda. */
fun Context.navigateToBusqueda() {
    startActivity(busquedaIntent(this))
}

/** Abre el detalle de una película por título (catálogo en `movieDetailForTitle`). */
fun Context.navigateToMovieDetail(movieTitle: String) {
    startActivity(detallePeliculaIntent(this, movieTitle))
}

/**
 * Navega según la pestaña inferior.
 * - Desde el dashboard: abre la pantalla sin cerrar el dashboard.
 * - Desde otra pantalla: abre el destino y cierra la actual (excepto Inicio, que usa CLEAR_TOP).
 */
fun Context.navigateToBottomRoute(target: BottomRoute) {
    val activity = this as? Activity ?: return
    when {
        target == BottomRoute.Inicio && activity is DashboardActivity -> return
        target == BottomRoute.Historial && activity is HistorialActivity -> return
        target == BottomRoute.MiLista && activity is MiListaActivity -> return
        target == BottomRoute.Favoritos && activity is FavoritosActivity -> return
        target == BottomRoute.Perfil && activity is PerfilActivity -> return
    }
    when {
        target == BottomRoute.Inicio -> {
            activity.startActivity(dashboardIntent(activity))
        }
        activity is DashboardActivity -> {
            activity.startActivity(secondaryIntent(activity, target))
        }
        else -> {
            activity.startActivity(secondaryIntent(activity, target))
            activity.finish()
        }
    }
}
