package com.jgene.aijobfinder.data.repository

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.jgene.aijobfinder.R
import com.jgene.aijobfinder.data.model.User
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val context: Context
) {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun getGoogleSignInClient() =
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )

    suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val result = auth.signInWithCredential(credential).await()

        val user = result.user ?: return
        val fcmToken = FirebaseMessaging.getInstance().token.await()

        val userData = User(
            uid = user.uid,
            name = user.displayName ?: "",
            email = user.email ?: "",
            photoUrl = user.photoUrl?.toString() ?: "",
            fcmToken = fcmToken,
            isGuest = false
        )

        firestore.collection("users")
            .document(user.uid)
            .set(userData)
            .await()
    }

    suspend fun anonymousLogin() {
        auth.signInAnonymously().await()
    }
}
