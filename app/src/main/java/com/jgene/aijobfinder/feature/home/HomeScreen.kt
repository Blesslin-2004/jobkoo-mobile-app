package com.jgene.aijobfinder.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgene.aijobfinder.JobKooApp
import com.jgene.aijobfinder.di.JobViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddClick: () -> Unit,
    onJobClick: (Job) -> Unit
) {
    // ✅ Get AppContainer
    val app = LocalContext.current.applicationContext as JobKooApp
    val factory = remember {
        JobViewModelFactory(app.container.jobSearchRepository)
    }

    // ✅ Correct ViewModel creation
    val homeViewModel: HomeViewModel =
        viewModel(factory = factory)

    val jobs by homeViewModel.jobs.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Search Jobs"
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Text(
                text = "Recent Jobs",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )

            if (jobs.isEmpty()) {
                EmptyState()
            } else {
                JobList(
                    jobs = jobs,
                    onClick = onJobClick
                )
            }
        }
    }
}
