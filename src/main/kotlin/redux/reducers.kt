package redux

import data.*

fun presentsReducer(state: Presents, action: RAction, id: Int = -1) =
    when (action) {
        is ChangePresent ->
            state.toMutableMap().apply {
                this[action.lessonID]?.let {
                    val old = it[action.studentID] ?: false
                    (it as MutableMap)[action.studentID] = !old
                }
            }
        is AddLesson ->
            state.plus(id to state.values.first().keys.associateWith { false })
        is AddStudent ->
            HashMap<Int, Map<Int, Boolean>>().toMutableMap().apply {
                state.map {
                    put(it.key, it.value.plus(id to false))
                }
            }
        is RemoveLesson -> state.minus(action.id)
        is RemoveStudent ->
            HashMap<Int, Map<Int, Boolean>>().toMutableMap().apply {
                state.map {
                    put(it.key, it.value.minus(action.id))
                }
            }
        else -> state
    }

fun lessonsReducer(state: LessonState, action: RAction, newId: Int = -1) =
    when (action) {
        is AddLesson -> state + (newId to action.lesson)
        is RemoveLesson -> state.minus(action.id)
        is ChangeLesson ->
            state.toMutableMap()
                .apply {
                    this[action.id] = action.newLesson
                }
        else -> state
    }

fun studentsReducer(state: StudentState, action: RAction, newId: Int = -1) =
    when (action) {
        is AddStudent -> state + (newId to action.student)
        is RemoveStudent -> state.minus(action.id)
        is ChangeStudent ->
            state.toMutableMap()
                .apply {
                    this[action.id] = action.newStudent
                }
        else -> state
    }

fun rootReducer(state: State, action: RAction) =
    when (action) {
        is AddLesson -> {
            val id = state.lessons.newId()
            State(
                lessonsReducer(state.lessons, action, id),
                studentsReducer(state.students, action),
                presentsReducer(state.presents, action, id)
            )
        }
        is AddStudent -> {
            val id = state.students.newId()
            State(
                lessonsReducer(state.lessons, action),
                studentsReducer(state.students, action, id),
                presentsReducer(state.presents, action, id)
            )
        }
        else ->
            State(
                lessonsReducer(state.lessons, action),
                studentsReducer(state.students, action),
                presentsReducer(state.presents, action)
            )
    }