package com.example.agrinyay.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun getCurrentUser() = auth.currentUser

    suspend fun login(
        email: String,
        password: String
    ): Result<String> {

        return try {

            val result = auth
                .signInWithEmailAndPassword(email, password)
                .await()

            val uid = result.user?.uid
                ?: return Result.failure(Exception("User not found"))

            Result.success(uid)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signup(
        email: String,
        password: String,
        role: String
    ): Result<String> {

        return try {

            val result = auth
                .createUserWithEmailAndPassword(email, password)
                .await()

            val uid = result.user?.uid
                ?: return Result.failure(Exception("User not created"))

            firestore.collection("users")
                .document(uid)
                .set(mapOf("role" to role))
                .await()

            Result.success(uid)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserRole(uid: String): String {

        val snapshot = firestore
            .collection("users")
            .document(uid)
            .get()
            .await()

        return snapshot.getString("role") ?: "farmer"
    }

    fun logout() {
        auth.signOut()
    }
}