package com.jgene.aijobfinder.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CityPickerDialog(
    onCitySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val allCities = listOf(
        "Chennai",
        "Bangalore",
        "Pune",
        "Hyderabad",
        "Mumbai",
        "Delhi"
    )

    var search by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = { Text("Select city") },
        text = {
            Column {
                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    placeholder = { Text("Search city") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                LazyColumn {
                    items(
                        allCities.filter {
                            it.contains(search, ignoreCase = true)
                        }
                    ) { city ->
                        Text(
                            text = city,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCitySelected(city)
                                    onDismiss()
                                }
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    )
}
