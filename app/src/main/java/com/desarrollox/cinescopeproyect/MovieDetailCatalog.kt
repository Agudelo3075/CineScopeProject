package com.desarrollox.cinescopeproyect

import androidx.compose.ui.graphics.Color

data class CastMemberUi(
    val actorLabel: String,
    val characterName: String,
    val avatar1: Color,
    val avatar2: Color
)

data class MovieDetailUi(
    val title: String,
    val year: String,
    val genres: String,
    val duration: String,
    val rating: String,
    val director: String,
    val synopsis: String,
    val color1: Color,
    val color2: Color,
    val imageUrl: String = "",
    val cast: List<CastMemberUi>
)

private val defaultSynopsis =
    "Una historia de superación y aventura que te mantendrá al borde del asiento hasta el final."

private fun castGeneric(title: String) = listOf(
    CastMemberUi("Lead A", "PROTA", Color(0xFF3D2C1E), Color(0xFF5C4033)),
    CastMemberUi("Lead B", "PROTA 2", Color(0xFF1E3D2C), Color(0xFF2C5C40)),
    CastMemberUi("Support", "ALIADO", Color(0xFF2C1E3D), Color(0xFF40335C))
)

fun movieDetailForTitle(title: String): MovieDetailUi {
    val t = title.trim()
    return when {
        t.equals("Interstellar", ignoreCase = true) || t.equals("INTERSTELLAR", ignoreCase = true) ->
            MovieDetailUi(
                title = "Interstellar",
                year = "2014",
                genres = "Sci-Fi / Adventure",
                duration = "2h 49m",
                rating = "8.7",
                director = "Dir. Christopher Nolan",
                synopsis = "En un futuro cercano, la Tierra agoniza. Un grupo de exploradores atraviesa un agujero de gusano en busca de un nuevo hogar para la humanidad, desafiando el tiempo y el espacio.",
                color1 = Color(0xFF000510),
                color2 = Color(0xFF002050),
                cast = listOf(
                    CastMemberUi("Matthew...", "COOPER", Color(0xFF4A3728), Color(0xFF2C1810)),
                    CastMemberUi("Anne...", "BRAND", Color(0xFF3D2834), Color(0xFF1A0A14)),
                    CastMemberUi("Jessica...", "MURPH", Color(0xFF2C3D28), Color(0xFF142010)),
                    CastMemberUi("Michael...", "MANN", Color(0xFF28343D), Color(0xFF101820))
                )
            )

        t.equals("The Dark Knight", ignoreCase = true) ->
            MovieDetailUi(
                title = "The Dark Knight",
                year = "2008",
                genres = "Action / Drama",
                duration = "2h 32m",
                rating = "9.0",
                director = "Dir. Christopher Nolan",
                synopsis = "Batman se enfrenta al Joker, un criminal caótico que sume Gotham en el terror mientras el Caballero Oscuro cuestiona hasta dónde puede llegar para detenerlo.",
                color1 = Color(0xFF1A1A2E),
                color2 = Color(0xFF16213E),
                cast = castGeneric(t)
            )

        t.equals("Inception", ignoreCase = true) ->
            MovieDetailUi(
                title = "Inception",
                year = "2010",
                genres = "Sci-Fi / Thriller",
                duration = "2h 28m",
                rating = "8.8",
                director = "Dir. Christopher Nolan",
                synopsis = "Un ladrón experto en extraer secretos del subconsciente recibe una misión imposible: implantar una idea en la mente de un objetivo mediante los sueños.",
                color1 = Color(0xFF2D1B00),
                color2 = Color(0xFF4A2F00),
                cast = castGeneric(t)
            )

        t.equals("The Godfather", ignoreCase = true) ->
            MovieDetailUi(
                title = "The Godfather",
                year = "1972",
                genres = "Crime / Drama",
                duration = "2h 55m",
                rating = "9.2",
                director = "Dir. Francis Ford Coppola",
                synopsis = "La saga de la familia Corleone y su imperio criminal en Nueva York, entre lealtad, poder y traición.",
                color1 = Color(0xFF1A0A00),
                color2 = Color(0xFF2E1500),
                cast = castGeneric(t)
            )

        t.equals("Pulp Fiction", ignoreCase = true) ->
            MovieDetailUi(
                title = "Pulp Fiction",
                year = "1994",
                genres = "Crime / Drama",
                duration = "2h 34m",
                rating = "8.9",
                director = "Dir. Quentin Tarantino",
                synopsis = "Varias historias entrelazadas de criminales, boxeadores y gánsteres en Los Ángeles.",
                color1 = Color(0xFF0A1A00),
                color2 = Color(0xFF152E00),
                cast = castGeneric(t)
            )

        else -> MovieDetailUi(
            title = t.ifEmpty { "Película" },
            year = "2020",
            genres = "Drama",
            duration = "2h 00m",
            rating = "8.0",
            director = "Dir. Director",
            synopsis = defaultSynopsis,
            color1 = Color(0xFF1A1A1A),
            color2 = Color(0xFF2E2E2E),
            cast = castGeneric(t)
        )
    }
}
