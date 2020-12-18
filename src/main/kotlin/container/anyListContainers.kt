package container

import component.*
import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.redux.rConnect
import redux.*

interface AnyListDispatchProps : RProps {
    var add: (Event) -> Unit
    var remove: (Int) -> Unit
}

interface AnyListStateProps<O> : RProps {
    var objs: Map<Int, O>
}

val studentListHOC =
    rConnect<
            State,
            RAction,
            WrapperAction,
            RProps,                         // Own Props
            AnyListStateProps<Student>,
            AnyListDispatchProps,
            AnyListProps<Student>
            >(
        mapStateToProps = { state, _ ->
            objs = state.students
        },
        mapDispatchToProps = { dispatch, _ ->
            add = { dispatch(AddStudent(Student("new", "student"))) }
            remove = { dispatch(RemoveStudent(it)) }
        }
    )

val studentListRClass =
    withDisplayName(
        "StudentList",
        fAnyList("Student", "/students", RBuilder::student)
    )
        .unsafeCast<RClass<AnyListProps<Student>>>()

val studentListContainer =
    studentListHOC(studentListRClass)

val lessonListContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            RProps,
            AnyListStateProps<Lesson>,
            AnyListDispatchProps,
            AnyListProps<Lesson>
            >(
        { state, _ ->
            objs = state.lessons
        },
        { dispatch, _ ->
            add = { dispatch(AddLesson(Lesson("new lesson"))) }
            remove = { dispatch(RemoveLesson(it)) }
        }
    )(
        withDisplayName(
            "LessonList",
            fAnyList("Lesson", "/lessons", RBuilder::lesson)
        )
            .unsafeCast<RClass<AnyListProps<Lesson>>>()
    )
