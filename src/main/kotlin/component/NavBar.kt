package component

import data.State
import hoc.withDisplayName
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RProps
import react.child
import react.dom.a
import react.dom.div
import react.dom.h1
import react.functionalComponent
import redux.*

interface NavBarProps : RProps {
    var toggleSignForm: (Event) -> Unit
    var store: Store<State, RAction, WrapperAction>
}

val fNavBar =
    functionalComponent<NavBarProps> {props->
        div ("navbar"){
                div("navbar_container") {
                    h1("navbar_container_name") {
                       + "HeadHunter"
                    }
                    div("navbar_container_buttons") {
                        if (props.store.getState().user!=null && !props.store.getState().cabinet ) {
                            div {
                                a {
                                    +props.store.getState().user!!.username
                                    attrs.onClickFunction = {
                                        props.store.dispatch(Cabinet(true))
                                    }
                                }
                                a {
                                    +"Выйти"
                                    attrs.onClickFunction = {
                                        props.store.dispatch(UserChange(null))
                                    }
                                }
                            }
                        }
                         if (props.store.getState().cabinet) {
                            div {
                                a {
                                    +"Все резюме"
                                    attrs.onClickFunction = {
                                        props.store.dispatch(Cabinet(false))
                                    }
                                }
                                a {
                                    +"Выйти"
                                    attrs.onClickFunction = {
                                        props.store.dispatch(UserChange(null))
                                    }
                                }
                            }
                        }
                        if(props.store.getState().user==null) {
                            a { attrs.onClickFunction = props.toggleSignForm
                                + "Войти / Зарегистрироваться" }
                        }
                    }
                }
        }
    }

fun RBuilder.navbar(
    toggleSignForm: (Event) -> Unit,
    store: Store<State, RAction, WrapperAction>
) = child(
    withDisplayName("Lesson", fNavBar)
) {
    attrs.toggleSignForm = toggleSignForm
    attrs.store = store
}