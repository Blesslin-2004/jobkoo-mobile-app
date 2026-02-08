package com.jgene.aijobfinder.feature.home

data class HomeUiState(
    val jobs: List<Job> = emptyList(),
    val isBottomSheetOpen: Boolean = false,
    val isLoading: Boolean = false
)
