package com.jgene.aijobfinder.feature.home

import android.net.Uri

data class HomeUiState(
    val jobs: List<Job> = emptyList(),
    val isBottomSheetOpen: Boolean = false,
    val filters: JobFilter? = null
)

