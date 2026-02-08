package com.jgene.aijobfinder.feature.home.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.jgene.aijobfinder.feature.home.Job

@Composable
fun JobDetailDialog(job: Job, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            TextButton(onClick = onClose) { Text("Close") }
        },
        title = { Text(job.jobTitle) },
        text = {
            Text(
                "Company: ${job.company}\n\n" +
                        "Cover Letter:\n${job.coverLetter}\n\n" +
                        "Notes:\n${job.applicationNotes}\n\n" +
                        "Apply:\n${job.jobUrl}"
            )
        }
    )
}
