package com.jgene.aijobfinder.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.jgene.aijobfinder.feature.home.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class FirestoreJobRepository {

    private val db = FirebaseFirestore.getInstance()

    fun observeJobs() = callbackFlow {
        val listener = db.collection("jobs")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, _ ->
                val jobs = snap?.documents?.mapNotNull {
                    it.toObject(Job::class.java)
                } ?: emptyList()
                trySend(jobs)
            }
        awaitClose { listener.remove() }
    }
}
