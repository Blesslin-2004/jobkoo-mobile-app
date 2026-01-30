package com.jgene.aijobfinder.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun JobTypeRadioGroup(
    selectedType: String,
    onSelected: (String) -> Unit
) {
    val jobTypes = listOf("On-site", "Remote", "Hybrid")

    Column {
        jobTypes.forEach { type ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedType == type,
                    onClick = { onSelected(type) }
                )
                Text(type)
            }
        }
    }
}
