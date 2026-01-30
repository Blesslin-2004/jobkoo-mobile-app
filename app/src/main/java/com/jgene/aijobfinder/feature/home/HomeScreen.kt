package com.jgene.aijobfinder.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jgene.aijobfinder.R
import androidx.compose.foundation.isSystemInDarkTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val isDarkTheme = isSystemInDarkTheme()

    val logoRes = if (isDarkTheme) {
        R.drawable.jobkoologowhite
    } else {
        R.drawable.jobkoologo
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openBottomSheet() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Search Jobs"
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

// -------- Top Header --------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "JobKoo",
                    style = MaterialTheme.typography.headlineMedium
                )

                Image(
                    painter = painterResource(id = logoRes),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                )
            }


            // -------- Recent Title --------
            Text(
                text = "Recent",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // -------- Job List --------
            JobList(
                jobs = uiState.jobs,
                modifier = Modifier.weight(1f)
            )
        }

        // -------- Bottom Sheet --------
        if (uiState.isBottomSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.closeBottomSheet() },
                sheetState = sheetState
            ) {
                SearchBottomSheet(
                    onApply = { filter ->
                        viewModel.applyFilters(filter)
                    },
                    onClose = {
                        viewModel.closeBottomSheet()
                    }
                )
            }
        }
    }
}
