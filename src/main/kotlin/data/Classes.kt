package data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
class Course (
    val id: Int,
    val price: String,
    val title: String,
    val description: String,
    val image: String,
    val link: String,
)
