package com.jgene.aijobfinder.feature.home

import android.net.Uri

data class JobFilter(
    val role: String,
    val locations: List<String>,
    val jobTypes: List<String>,
    val resumeUri: Uri? = null
)
