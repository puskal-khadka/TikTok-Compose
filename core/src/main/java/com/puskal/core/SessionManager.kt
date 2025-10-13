package com.puskal.core

/**
 * Created by Puskal Khadka on 11/20/2023.
 */

object SessionManager {
    var isLoggedIn: Boolean = false
        private set
    var email: String? = null
        private set

    /**
     * Mark the session as logged in with the provided [email].
     */
    fun logIn(email: String) {
        SessionManager.email = email
        isLoggedIn = true
    }

    /**
     * Clear the session and mark the user as logged out.
     */
    fun logOut() {
        email = null
        isLoggedIn = false
    }
}
