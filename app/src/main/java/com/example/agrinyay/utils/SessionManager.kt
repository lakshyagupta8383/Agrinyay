package com.example.agrinyay.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Professional Session Manager
 * Handles local user data persistence securely and cleanly.
 */
class SessionManager(context: Context) {

    // Private mode: only this app can access these preferences
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    companion object {
        private const val PREF_NAME = "AgriNyaySession"
        private const val KEY_ROLE = "user_role" // "farmer" or "customer"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    /**
     * Saves the login session.
     * @param role: The user's role ("farmer" or "customer")
     */
    fun createLoginSession(role: String) {
        editor.putString(KEY_ROLE, role)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply() // Asynchronous save for performance
    }

    /**
     * Gets the saved user role. Defaults to "farmer" if not found.
     */
    fun getUserRole(): String {
        return prefs.getString(KEY_ROLE, "farmer") ?: "farmer"
    }

    /**
     * Checks if the user is already logged in.
     */
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    /**
     * Clears session details (Logout).
     */
    fun logoutUser() {
        editor.clear()
        editor.apply()
    }
}