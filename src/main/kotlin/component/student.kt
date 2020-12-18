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

interface StudentProps : RProps {
    var student: Student
    var cssClass: String
    var onClick: (Event) -> Unit
}

val fStudent =
    functionalComponent<StudentProps> {
        span(
            it.cssClass
        ) {
            +"${it.student.firstname} ${it.student.surname}"
            attrs.onClickFunction = it.onClick
        }
    }

fun RBuilder.student(
    student: Student,
    cssClass: String,
    onClick: (Event) -> Unit
) = child(
    withDisplayName("Student", fStudent)
) {
    attrs.student = student
    attrs.cssClass = cssClass
    attrs.onClick = onClick
}

interface StudentEditProps : RProps {
    var student: Pair<Int, Student>
    var onClick: (Student) -> Unit
}

val fStudentEdit =
    functionalComponent<StudentEditProps> { props ->
        span {
            input() {
                attrs.id = "firstname${props.student.first}"
                attrs.defaultValue = props.student.second.firstname
            }
            input() {
                attrs.id = "surname${props.student.first}"
                attrs.defaultValue = props.student.second.surname
            }
            button {
                +"Save"
                attrs.onClickFunction = {
                    val firstname = document
                        .getElementById("firstname${props.student.first}")
                            as HTMLInputElement
                    val surname = document
                        .getElementById("surname${props.student.first}")
                            as HTMLInputElement
                    props.onClick(Student(firstname.value, surname.value))
                    window.history.back()
                }
            }
        }
    }
