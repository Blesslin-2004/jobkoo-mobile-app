package com.jgene.aijobfinder.feature.home

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jgene.aijobfinder.feature.home.components.CityChips
import com.jgene.aijobfinder.feature.home.components.CityPickerDialog
import com.jgene.aijobfinder.feature.home.components.JobRoleDropdown
import com.jgene.aijobfinder.feature.home.components.JobTypeRadioGroup
import com.jgene.aijobfinder.feature.home.components.ResumePicker

@Composable
fun SearchBottomSheet(
    onApply: (JobFilter) -> Unit,
    onClose: () -> Unit
) {
    // ---- UI States ----
    var selectedRole by remember { mutableStateOf("") }
    val selectedCities = remember { mutableStateListOf<String>() }
    var selectedJobType by remember { mutableStateOf("Remote") }
    var resumeUri by remember { mutableStateOf<Uri?>(null) }
    var showCityDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        // -------- Looking for (searchable dropdown) --------
        JobRoleDropdown(
            selectedRole = selectedRole,
            onRoleSelected = { selectedRole = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -------- Job location --------
        Text(
            text = "Job location",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        CityChips(
            cities = selectedCities,
            onRemove = { selectedCities.remove(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { showCityDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add city")
        }

        if (showCityDialog) {
            CityPickerDialog(
                onCitySelected = { city ->
                    if (!selectedCities.contains(city)) {
                        selectedCities.add(city)
                    }
                },
                onDismiss = { showCityDialog = false }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -------- Job type --------
        Text(
            text = "Job type",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        JobTypeRadioGroup(
            selectedType = selectedJobType,
            onSelected = { selectedJobType = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -------- Resume upload --------
        ResumePicker(
            onPdfSelected = { resumeUri = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // -------- Start searching --------
        Button(
            onClick = {
                onApply(
                    JobFilter(
                        role = selectedRole,
                        locations = selectedCities.toList(),
                        jobTypes = listOf(selectedJobType),
                        resumeUri = resumeUri
                    )
                )
                onClose()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(50)
        ) {
            Text("Start searching")
        }
    }
}
