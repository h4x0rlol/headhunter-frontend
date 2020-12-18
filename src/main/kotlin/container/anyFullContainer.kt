package container

import component.*
import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import redux.*
import react.redux.rConnect

interface AnyFullDispatchProps : RProps {
    var onClick: (Int) -> (Event) -> Unit
}

interface AnyFullStateProps<O, S> : RProps {
    var subobjs: Map<Int, S>
    var presents: Map<Int, Boolean>?
}

interface AnyFullOwnProps<O> : RProps {
    var obj: Pair<Int, O>
}

val lessonFullContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            AnyFullOwnProps<Lesson>,
            AnyFullStateProps<Lesson, Student>,
            AnyFullDispatchProps,
            AnyFullProps<Lesson, Student>>(
        { state, ownProps ->
            subobjs = state.students
            presents = state.presents[ownProps.obj.first]
        },
        { dispatch, ownProps ->
            onClick =
                { index ->
                    {
                        dispatch(ChangePresent(ownProps.obj.first, index))
                    }
                }
        }
    )(
        withDisplayName(
            "LessonFull",
            fAnyFull<Lesson, Student>(RBuilder::student)
        )
            .unsafeCast<RClass<AnyFullProps<Lesson, Student>>>()
    )

val studentFullContainer =
    rConnect<
            State,
            RAction,
            WrapperAction,
            AnyFullOwnProps<Student>,
            AnyFullStateProps<Student, Lesson>,
            AnyFullDispatchProps,
            AnyFullProps<Student, Lesson>>(
        { state, ownProps ->
            subobjs = state.lessons
            presents = state.presentsStudent(ownProps.obj.first)
        },
        { dispatch, ownProps ->
            onClick = { index ->
                {
                    dispatch(ChangePresent(index, ownProps.obj.first))
                }
            }
        }
    )(
        withDisplayName(
            "StudentFull",
            fAnyFull<Student, Lesson>(RBuilder::lesson)
        )
            .unsafeCast<RClass<AnyFullProps<Student, Lesson>>>()
    )