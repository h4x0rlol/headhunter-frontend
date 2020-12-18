package component

import react.*
import react.dom.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import data.*
import hoc.withDisplayName
import kotlinx.html.id
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlin.browser.window

interface LessonProps : RProps {
    var lesson: Lesson
    var cssClass: String
    var onClick: (Event) -> Unit
}

val fLesson =
    functionalComponent<LessonProps> {
        span(
            it.cssClass
        ) {
            +it.lesson.name
            attrs.onClickFunction = it.onClick
        }
    }

fun RBuilder.lesson(
    lesson: Lesson,
    cssClass: String,
    onClick: (Event) -> Unit
) = child(
    withDisplayName("Lesson", fLesson)
) {
    attrs.lesson = lesson
    attrs.cssClass = cssClass
    attrs.onClick = onClick
}

interface LessonEditProps : RProps {
    var lesson: Pair<Int, Lesson>
    var onClick: (Lesson) -> Unit
}

val fLessonEdit =
    functionalComponent<LessonEditProps> { props ->
        span {
            input() {
                attrs.id = "lessonEdit${props.lesson.first}"
                attrs.defaultValue = props.lesson.second.name
            }
            button {
                +"Save"
                attrs.onClickFunction = {
                    val inputElement = document
                        .getElementById("lessonEdit${props.lesson.first}")
                            as HTMLInputElement
                    props.onClick(Lesson(inputElement.value))
                    window.history.back()
                }
            }
        }
    }
