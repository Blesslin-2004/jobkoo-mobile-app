package com.jgene.aijobfinder.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgene.aijobfinder.JobKooApp
import com.jgene.aijobfinder.di.JobViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBottomSheet(
    onDismiss: () -> Unit
) {
    // ✅ Get AppContainer safely
    val app = LocalContext.current.applicationContext as JobKooApp
    val factory = remember {
        JobViewModelFactory(app.container.jobSearchRepository)
    }

    // ✅ Correct ViewModel creation
    val searchViewModel: SearchViewModel =
        viewModel(factory = factory)

    val uiState by searchViewModel.uiState.collectAsState()

    var role by remember { mutableStateOf("Android Developer") }
    var location by remember { mutableStateOf("chennai") }
    var jobType by remember { mutableStateOf("Remote") }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "Looking for",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = role,
                onValueChange = { role = it },
                label = { Text("Role") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = jobType,
                onValueChange = { jobType = it },
                label = { Text("Job Type") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.loading,
                onClick = {
                    searchViewModel.startSearch(
                        role = role,
                        locations = location,
                        jobType = jobType
                    )
                    onDismiss()
                }
            ) {
                Text("Start searching")
            }
        }

        // ✅ PROGRESS LOADER
        if (uiState.loading) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.6f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
