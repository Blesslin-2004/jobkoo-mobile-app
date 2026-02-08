package com.jgene.aijobfinder.feature.home.components

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext

@Composable
fun ResumePicker(
    selectedUri: Uri?,
    onPdfSelected: (Uri) -> Unit
) {
    val context = LocalContext.current

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let { onPdfSelected(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(12.dp)
            )
            .clickable {
                picker.launch(arrayOf("application/pdf"))
            },
        contentAlignment = Alignment.Center
    ) {
        if (selectedUri == null) {
            Text("Click to upload a resume")
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = getFileName(context, selectedUri),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

fun getFileName(context: Context, uri: Uri): String {
    val cursor = context.contentResolver.query(
        uri, null, null, null, null
    )
    cursor?.use {
        val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst() && index >= 0) {
            return it.getString(index)
        }
    }
    return "Resume.pdf"
}



