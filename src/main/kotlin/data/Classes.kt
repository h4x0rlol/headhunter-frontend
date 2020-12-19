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
