package com.jgene.aijobfinder.di

import com.google.firebase.firestore.FirebaseFirestore
import com.jgene.aijobfinder.data.remote.N8nJobSearchService
import com.jgene.aijobfinder.data.repository.JobSearchRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomx123.app.n8n.cloud/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val n8nApi: N8nJobSearchService =
        retrofit.create(N8nJobSearchService::class.java)

    private val firestore = FirebaseFirestore.getInstance()

    val jobSearchRepository = JobSearchRepository(
        api = n8nApi,
        firestore = firestore
    )
}
