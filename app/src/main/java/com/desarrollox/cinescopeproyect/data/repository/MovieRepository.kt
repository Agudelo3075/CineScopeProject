package com.desarrollox.cinescopeproyect.data.repository

import android.content.Context
import com.desarrollox.cinescopeproyect.data.local.DatabaseProvider
import com.desarrollox.cinescopeproyect.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

class MovieRepository(context: Context) {
    private val movieDao = DatabaseProvider.getMovieDao(context)

    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    fun getPopularMovies(): Flow<List<MovieEntity>> = movieDao.getPopularMovies()

    fun getTopRatedMovies(): Flow<List<MovieEntity>> = movieDao.getTopRatedMovies()

    fun getNewReleaseMovies(): Flow<List<MovieEntity>> = movieDao.getNewReleaseMovies()

    fun searchMovies(query: String): Flow<List<MovieEntity>> = movieDao.searchMovies(query)

    suspend fun getMovieById(id: Long): MovieEntity? = movieDao.getMovieById(id)

    suspend fun getMovieByTitle(title: String): MovieEntity? = movieDao.getMovieByTitle(title)

    suspend fun insertMovie(movie: MovieEntity): Long = movieDao.insertMovie(movie)

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    suspend fun updateMovie(movie: MovieEntity) = movieDao.updateMovie(movie)

    suspend fun deleteMovie(movie: MovieEntity) = movieDao.deleteMovie(movie)

    suspend fun getMovieCount(): Int = movieDao.getMovieCount()

    suspend fun initializeMovies() {
        if (getMovieCount() == 0) {
            val defaultMovies = listOf(
                MovieEntity(
                    title = "Interstellar",
                    year = 2014,
                    genre = "Sci-Fi",
                    duration = "2h 49m",
                    rating = 8.6f,
                    director = "Christopher Nolan",
                    synopsis = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                    cast = "Matthew McConaughey, Anne Hathaway, Jessica Chastain",
                    isPopular = true,
                    isTopRated = true,
                    color1 = 0xFF1A237E,
                    color2 = 0xFF000051
                ),
                MovieEntity(
                    title = "The Dark Knight",
                    year = 2008,
                    genre = "Action",
                    duration = "2h 32m",
                    rating = 9.0f,
                    director = "Christopher Nolan",
                    synopsis = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                    cast = "Christian Bale, Heath Ledger, Aaron Eckhart",
                    isPopular = true,
                    isTopRated = true,
                    color1 = 0xFF37474F,
                    color2 = 0xFF102027
                ),
                MovieEntity(
                    title = "Inception",
                    year = 2010,
                    genre = "Sci-Fi",
                    duration = "2h 28m",
                    rating = 8.8f,
                    director = "Christopher Nolan",
                    synopsis = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                    cast = "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page",
                    isPopular = true,
                    isTopRated = true,
                    color1 = 0xFF1A237E,
                    color2 = 0xFF000051
                ),
                MovieEntity(
                    title = "The Godfather",
                    year = 1972,
                    genre = "Crime",
                    duration = "2h 55m",
                    rating = 9.2f,
                    director = "Francis Ford Coppola",
                    synopsis = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                    cast = "Marlon Brando, Al Pacino, James Caan",
                    isPopular = false,
                    isTopRated = true,
                    color1 = 0xFF4E342E,
                    color2 = 0xFF3E2723
                ),
                MovieEntity(
                    title = "Pulp Fiction",
                    year = 1994,
                    genre = "Crime",
                    duration = "2h 34m",
                    rating = 8.9f,
                    director = "Quentin Tarantino",
                    synopsis = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                    cast = "John Travolta, Uma Thurman, Samuel L. Jackson",
                    isPopular = true,
                    isTopRated = true,
                    color1 = 0xFF5D4037,
                    color2 = 0xFF3E2723
                ),
                MovieEntity(
                    title = "Dune",
                    year = 2021,
                    genre = "Sci-Fi",
                    duration = "2h 35m",
                    rating = 8.0f,
                    director = "Denis Villeneuve",
                    synopsis = "A noble family becomes embroiled in a war for control over the galaxy's most valuable asset while its heir becomes troubled by visions of a dark future.",
                    cast = "Timothée Chalamet, Rebecca Ferguson, Oscar Isaac",
                    isPopular = true,
                    isTopRated = false,
                    isNewRelease = true,
                    color1 = 0xFFD4A017,
                    color2 = 0xFF8B6914
                ),
                MovieEntity(
                    title = "Oppenheimer",
                    year = 2023,
                    genre = "Drama",
                    duration = "3h 0m",
                    rating = 8.4f,
                    director = "Christopher Nolan",
                    synopsis = "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.",
                    cast = "Cillian Murphy, Emily Blunt, Matt Damon",
                    isPopular = true,
                    isTopRated = false,
                    isNewRelease = true,
                    color1 = 0xFF3D5A80,
                    color2 = 0xFF1D3557
                ),
                MovieEntity(
                    title = "Everything Everywhere All at Once",
                    year = 2022,
                    genre = "Sci-Fi",
                    duration = "2h 19m",
                    rating = 7.8f,
                    director = "Daniels",
                    synopsis = "A middle-aged Chinese immigrant is swept up into an insane adventure where she alone can save existence by exploring other universes and connecting with the lives she could have led.",
                    cast = "Michelle Yeoh, Stephanie Hsu, Ke Huy Quan",
                    isPopular = false,
                    isTopRated = true,
                    isNewRelease = true,
                    color1 = 0xFFFF6B6B,
                    color2 = 0xFFEE5A5A
                )
            )
            insertMovies(defaultMovies)
        }
    }
}
