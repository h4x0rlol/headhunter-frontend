package container

import react.*
import redux.*
import react.redux.rConnect
import component.*
import data.*
import hoc.withDisplayName

interface LessonEditOwnProps : RProps {
    var lesson: Pair<Int, Lesson>
}

val lessonEditContainer =
    rConnect<
            RAction,
            WrapperAction,
            LessonEditOwnProps,
            LessonEditProps
            >(
        { dispatch, ownProps ->
            onClick = {
                dispatch(ChangeLesson(ownProps.lesson.first, it))
            }
        }
    )(
        withDisplayName(
            "LessonEdit",
            fLessonEdit
        ).unsafeCast<RClass<LessonEditProps>>()
    )

interface StudentEditOwnProps : RProps {
    var student: Pair<Int, Student>
}

val studentEditContainer =
    rConnect<
            RAction,
            WrapperAction,
            StudentEditOwnProps,
            StudentEditProps
            >(
        { dispatch, ownProps ->
            onClick = {
                dispatch(ChangeStudent(ownProps.student.first, it))
            }
        }
    )(
        withDisplayName(
            "StudentEdit",
            fStudentEdit
        ).unsafeCast<RClass<StudentEditProps>>()
    )
