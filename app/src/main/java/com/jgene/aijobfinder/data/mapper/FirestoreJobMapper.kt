package com.jgene.aijobfinder.data.mapper

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.jgene.aijobfinder.feature.home.Job

/**
 * Convert Job → Firestore Map
 */
fun Job.toFirestoreMap(): Map<String, Any?> {
    return mapOf(
        "jobId" to jobId,
        "jobTitle" to jobTitle,
        "company" to company,
        "jobUrl" to jobUrl,
        "coverLetter" to coverLetter,
        "matchScore" to matchScore,
        "applicationNotes" to applicationNotes,
        "fetchedAt" to FieldValue.serverTimestamp()
    )
}

/**
 * Convert Firestore → Job
 */
fun firestoreToJob(
    data: Map<String, Any?>
): Job {
    return Job(
        jobId = data["jobId"] as? String ?: "",
        jobTitle = data["jobTitle"] as? String ?: "",
        company = data["company"] as? String ?: "",
        jobUrl = data["jobUrl"] as? String ?: "",
        coverLetter = data["coverLetter"] as? String ?: "",
        matchScore = (data["matchScore"] as? Number)?.toInt() ?: 0,
        applicationNotes = data["applicationNotes"] as? String ?: ""
    )
}
