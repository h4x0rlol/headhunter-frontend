package data

class State(
    val user: User?
)

fun initialState() =
    State(null)
