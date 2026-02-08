package com.jgene.aijobfinder.feature.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun JobList(
    jobs: List<Job>,
    onClick: (Job) -> Unit
) {
    LazyColumn {
        items(
            items = jobs,
            key = { it.jobId }
        ) { job ->
            JobCard(
                job = job,
                onClick = { onClick(job) }
            )
        }
    }
}
