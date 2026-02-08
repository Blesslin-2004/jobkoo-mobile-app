package com.jgene.aijobfinder.feature.home

import com.google.firebase.Timestamp

data class Job(
    val jobId: String = "",
    val jobTitle: String = "",
    val company: String = "",
    val jobUrl: String = "",
    val coverLetter: String = "",
    val applicationNotes: String = "",
    val matchScore: Int = 0,
    val createdAt: Timestamp? = null
)
