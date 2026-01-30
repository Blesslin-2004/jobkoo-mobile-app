package com.jgene.aijobfinder.feature.home.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobRoleDropdown(
    selectedRole: String,
    onRoleSelected: (String) -> Unit
) {
    val jobRoles = listOf(
        "Android Developer",
        "Backend Developer",
        "Frontend Developer",
        "Full Stack Developer",
        "AI Engineer",
        "Data Scientist",
        "QA Engineer"
    )

    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf(selectedRole) }

    val filteredRoles = jobRoles.filter {
        it.contains(searchText, ignoreCase = true)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                expanded = true
            },
            label = { Text("Looking for") },
            modifier = Modifier.menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredRoles.forEach { role ->
                DropdownMenuItem(
                    text = { Text(role) },
                    onClick = {
                        searchText = role
                        onRoleSelected(role)
                        expanded = false
                    }
                )
            }
        }
    }
}
