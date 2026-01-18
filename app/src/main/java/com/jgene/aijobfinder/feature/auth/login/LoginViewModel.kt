package com.jgene.aijobfinder.feature.auth.login

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.jgene.aijobfinder.R
import com.jgene.aijobfinder.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    /**
     * GOOGLE SIGN-IN USING CREDENTIAL MANAGER
     */
    fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val credentialManager = CredentialManager.create(context)

                val googleIdOption = GetGoogleIdOption.Builder()
                    // IMPORTANT: Use WEB CLIENT ID
                    .setServerClientId(
                        context.getString(R.string.default_web_client_id)
                    )
                    .setFilterByAuthorizedAccounts(false)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )

                handleCredential(result.credential)

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Google sign-in failed", e)
                _errorMessage.value = e.localizedMessage
                _isLoading.value = false
            }
        }
    }

    /**
     * HANDLE CREDENTIAL RESULT
     */
    private suspend fun handleCredential(credential: Credential) {
        if (
            credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(credential.data)

            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
        } else {
            _errorMessage.value = "Invalid credential type"
            _isLoading.value = false
        }
    }

    /**
     * FIREBASE AUTH WITH GOOGLE ID TOKEN
     */
    private suspend fun firebaseAuthWithGoogle(idToken: String) {
        try {
            val firebaseCredential =
                GoogleAuthProvider.getCredential(idToken, null)

            val authResult =
                auth.signInWithCredential(firebaseCredential).await()

            val firebaseUser = authResult.user
                ?: throw Exception("Firebase user is null")

            val fcmToken = FirebaseMessaging.getInstance().token.await()

            val user = User(
                uid = firebaseUser.uid,
                name = firebaseUser.displayName ?: "",
                email = firebaseUser.email ?: "",
                photoUrl = firebaseUser.photoUrl?.toString() ?: "",
                fcmToken = fcmToken,
                isGuest = false
            )

            firestore.collection("users")
                .document(firebaseUser.uid)
                .set(user)
                .await()

            _loginSuccess.value = true

        } catch (e: Exception) {
            Log.e("LoginViewModel", "Firebase auth failed", e)
            _errorMessage.value = e.localizedMessage
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * GUEST / ANONYMOUS LOGIN
     */
    fun signInAsGuest() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                auth.signInAnonymously().await()
                _loginSuccess.value = true
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Guest login failed", e)
                _errorMessage.value = e.localizedMessage
            } finally {
                _isLoading.value = false
            }
        }
    }
}
