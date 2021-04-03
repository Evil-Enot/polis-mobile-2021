package ru.mail.polis.auth

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleAuthenticationService(private val singInClient: GoogleSignInClient) :
    AuthenticationService {

    companion object {
        private const val TAG = "AuthenticationService"
    }

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun getSignInIntent(): Intent {
        return singInClient.signInIntent
    }

    override fun handleResult(data: Intent?): Boolean {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "Google sign in failed", e)

            return false
        }

        return true
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
}