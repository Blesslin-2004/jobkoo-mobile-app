package com.jgene.aijobfinder.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val fcmToken: String = "",
    val isGuest: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
