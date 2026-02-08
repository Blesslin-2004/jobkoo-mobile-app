package com.jgene.aijobfinder.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.jgene.aijobfinder.data.remote.JobSearchRequest
import com.jgene.aijobfinder.data.remote.N8nJobSearchService
import com.jgene.aijobfinder.feature.home.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class JobSearchRepository(
    private val api: N8nJobSearchService,
    private val firestore: FirebaseFirestore
) {

    suspend fun triggerN8nSearch(
        role: String,
        locations: String,
        jobType: String
    ) {
        api.triggerSearch(
            JobSearchRequest(role, locations, jobType)
        )
    }

    fun listenJobs() = callbackFlow {
        val listener = firestore.collection("jobs")
            .orderBy("createdAt")
            .addSnapshotListener { snapshot, _ ->
                val jobs = snapshot?.documents?.mapNotNull {
                    it.toObject(Job::class.java)
                } ?: emptyList()

                trySend(jobs)
            }

        awaitClose { listener.remove() }
    }
}
