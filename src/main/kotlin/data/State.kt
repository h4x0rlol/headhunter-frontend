package data

typealias LessonState = Map<Int, Lesson>

typealias StudentState = Map<Int, Student>

typealias Presents = Map<Int, Map<Int, Boolean>>

class State(
    val lessons: LessonState,
    val students: StudentState,
    val presents: Presents
)

fun <T> Map<Int, T>.newId() =
    (this.maxBy { it.key }?.key ?: 0) + 1

fun State.presentsStudent(idStudent: Int) =
    presents.map {
        it.key to (it.value[idStudent] ?: false)
    }.toMap()


fun initialState() =
    State(
        lessonsList().mapIndexed { index, lesson ->
            index to lesson
        }.toMap(),
        studentList().mapIndexed { index, student ->
            index to student
        }.toMap(),
        lessonsList().mapIndexed { idLesson, _ ->
            idLesson to studentList().mapIndexed { idStudent, _ ->
                idStudent to false
            }.toMap()
        }.toMap()
    )
