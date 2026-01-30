package com.jgene.aijobfinder.feature.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _uiState = mutableStateOf(HomeUiState())
    val uiState: State<HomeUiState> = _uiState

    private val allJobs = listOf(
        Job("Android Developer", "Google", "Bangalore", "Remote"),
        Job("Backend Developer", "Amazon", "Chennai", "On-site"),
        Job("AI Engineer", "OpenAI", "Pune", "Hybrid"),
        Job("Frontend Developer", "Flipkart", "Bangalore", "Remote")
    )

    init {
        // initial recent jobs
        _uiState.value = _uiState.value.copy(jobs = allJobs)
    }

    fun openBottomSheet() {
        _uiState.value = _uiState.value.copy(isBottomSheetOpen = true)
    }

    fun closeBottomSheet() {
        _uiState.value = _uiState.value.copy(isBottomSheetOpen = false)
    }

    fun applyFilters(filter: JobFilter) {
        val filtered = allJobs.filter { job ->
            job.title.contains(filter.role, ignoreCase = true) &&
                    (filter.locations.isEmpty() || filter.locations.contains(job.location)) &&
                    filter.jobTypes.contains(job.type)
        }

        _uiState.value = _uiState.value.copy(
            jobs = filtered,
            isBottomSheetOpen = false
        )
    }
}
