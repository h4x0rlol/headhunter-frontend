package data

data class Student(
    val firstname: String,
    val surname: String
) {
    override fun toString(): String =
        "$firstname $surname"
}

fun studentList() =
    arrayOf(
        Student("Sheldon", "Cooper"),
        Student("Leonard", "Hofstadter"),
        Student("Howard", "Wolowitz")
    )