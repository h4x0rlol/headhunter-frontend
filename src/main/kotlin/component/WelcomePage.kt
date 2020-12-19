package component

import data.User
import hoc.withDisplayName
import kotlinx.html.classes
import react.RBuilder
import react.RProps
import react.child
import react.dom.*
import react.functionalComponent

interface WelcomePageProps : RProps {}

val fWelcomePage =
    functionalComponent<WelcomePageProps> {
            section ("course-title"){
                div("course-title_main") {
                    h1("course-title_main__title") { + "HeadHunter" }
                    h2("course-title_main__description") { + "Здесь вы можете найти себе работодателя или работников"  }
                }
            }
    }

fun RBuilder.welcomepage(
) = child(
    withDisplayName("Lesson", fWelcomePage)
) {
}