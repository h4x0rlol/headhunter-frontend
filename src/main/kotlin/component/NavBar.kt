package component

import data.User
import hoc.withDisplayName
import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RProps
import react.child
import react.dom.a
import react.dom.div
import react.dom.h1
import react.functionalComponent
import react.router.dom.redirect
import react.router.dom.useHistory

interface NavBarProps : RProps {
    var toggleSignForm: (Event) -> Unit
    var user: User?
}

val fNavBar =
    functionalComponent<NavBarProps> {props->
        div ("navbar"){
                div("navbar_container") {
                    h1("navbar_container_name") {
                       + "HeadHunter"
                    }
                    div("navbar_container_buttons") {
                        if (props.user!=null) {
                            div {
                                a {
                                    +"Ник"
                                }
                                a {
                                    +"Выйти"
                                }
                            }
                        }
                        else {
                            a { attrs.onClickFunction = props.toggleSignForm
                                + "Войти / Зарегистрироваться" }
                        }
                    }
                }
        }
    }

fun RBuilder.navbar(
    toggleSignForm: (Event) -> Unit
) = child(
    withDisplayName("Lesson", fNavBar)
) {
    attrs.toggleSignForm = toggleSignForm
}