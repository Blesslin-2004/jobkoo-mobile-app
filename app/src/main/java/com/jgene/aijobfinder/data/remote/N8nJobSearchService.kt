package com.jgene.aijobfinder.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

data class JobSearchRequest(
    val role: String,
    val locations: String,
    val jobType: String
)

interface N8nJobSearchService {

    @POST("webhook/2081b697-8068-4234-b0ff-0644ef347c9f")
    suspend fun triggerSearch(
        @Body request: JobSearchRequest
    )
}
