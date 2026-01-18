package com.jgene.aijobfinder.feature.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.jgene.aijobfinder.ui.components.OrDivider
import com.jgene.aijobfinder.ui.components.OutlinedPrimaryButton
import com.jgene.aijobfinder.ui.components.PrimaryButton

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = LoginViewModel()
) {
    val context = LocalContext.current
    val primaryBlue = Color(0xFF2F2CE9)
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val firebaseAuth = FirebaseAuth.getInstance()
    val currentuser = firebaseAuth.currentUser

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            onLoginSuccess()
        }
        if(currentuser != null){

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "JobKoo",
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = primaryBlue
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Find jobs that\nMatch you perfectly",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        PrimaryButton(
            text = "Continue with Google",
            backgroundColor = primaryBlue,
            onClick = {
                viewModel.signInWithGoogle(context)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        OrDivider()

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedPrimaryButton(
            text = "Continue with Guest",
            borderColor = primaryBlue,
            onClick = viewModel::signInAsGuest
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

