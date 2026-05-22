package com.desarrollox.cinescopeproyect.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.desarrollox.cinescopeproyect.data.local.DatabaseProvider
import com.desarrollox.cinescopeproyect.data.local.entity.*
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UserRepository(context: Context) {
    private val userDao = DatabaseProvider.getUserDao(context)
    private val favoriteDao = DatabaseProvider.getFavoriteDao(context)
    private val watchHistoryDao = DatabaseProvider.getWatchHistoryDao(context)
    private val myListDao = DatabaseProvider.getMyListDao(context)
    private val ratingDao = DatabaseProvider.getRatingDao(context)
    
    private val db = Firebase.firestore

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "cinescope_prefs", Context.MODE_PRIVATE
    )

    suspend fun login(email: String, password: String): Result<UserEntity> {
        val user = userDao.login(email, password)
        return if (user != null) {
            saveSession(user.id)
            Result.success(user)
        } else {
            Result.failure(Exception("Invalid email or password"))
        }
    }

    suspend fun register(fullName: String, email: String, password: String): Result<UserEntity> {
        return if (userDao.emailExists(email)) {
            Result.failure(Exception("Email already registered"))
        } else {
            val initials = fullName.split(" ")
                .take(2)
                .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                .joinToString("")
            
            val user = UserEntity(
                fullName = fullName,
                email = email,
                password = password,
                avatarInitials = initials.ifEmpty { fullName.take(2).uppercase() }
            )
            val id = userDao.insertUser(user)
            
            // Guardar también en Firebase Firestore
            val userMap = hashMapOf(
                "localId" to id,
                "fullName" to fullName,
                "email" to email,
                "avatarInitials" to user.avatarInitials
            )
            db.collection("users").document(id.toString())
                .set(userMap)
                .addOnSuccessListener {
                    Log.d("FirebaseSync", "Usuario guardado en Firestore con éxito.")
                }
                .addOnFailureListener { e ->
                    Log.e("FirebaseSync", "Error al guardar el usuario en Firestore.", e)
                }

            saveSession(id)
            Result.success(user.copy(id = id))
        }
    }

    suspend fun getCurrentUser(): UserEntity? {
        val userId = getLoggedInUserId()
        return if (userId > 0) userDao.getUserById(userId) else null
    }

    fun getCurrentUserFlow() = userDao.getUserByIdFlow(getLoggedInUserId())

    fun isLoggedIn(): Boolean = getLoggedInUserId() > 0

    private fun getLoggedInUserId(): Long = prefs.getLong("user_id", -1)

    private fun saveSession(userId: Long) {
        prefs.edit().putLong("user_id", userId).apply()
    }

    fun logout() {
        prefs.edit().remove("user_id").apply()
    }

    fun getFavoriteCount() = favoriteDao.getFavoriteCount(getLoggedInUserId())
    fun getWatchedCount() = watchHistoryDao.getCompletedCount(getLoggedInUserId())

    suspend fun getFavoriteCountSync(): Int {
        var count = 0
        favoriteDao.getFavoriteCount(getLoggedInUserId()).collect { count = it; return@collect }
        return count
    }

    suspend fun getWatchedCountSync(): Int {
        var count = 0
        watchHistoryDao.getCompletedCount(getLoggedInUserId()).collect { count = it; return@collect }
        return count
    }
}
