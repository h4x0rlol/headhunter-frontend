package data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer


@Serializable
class User (
    val id: Int,
    val username: String,
    val password: String,
    val status: Int
    )

@Serializable
class Resume (
        val id: Int,
        val userId: Int,
        val title: String,
        val description: String
)

@Serializable
class Reply (
        val id: Int,
        val resumeId: Int,
        val userId: Int
)