package component

import data.User
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*


interface SignFormProps : RProps {
    var toggleSignForm: (Event) -> Unit
}

val fSignForm =
    functionalComponent<SignFormProps> {props ->
        val (type, setType) = useState("signIn")
        val (username, setUsername) = useState("")
        val (password, setPassword) = useState("")


        div("modalSign") {
            div("modalDialogWrap") {
                div("modalDialogBg") {
                    div ("modalDialogBlock"){
                        div("signForm") {
                            header("signForm_header") {
                                div("signForm_header_elements") {
                                    div("signForm_header_elements__buttons") {
                                        a (classes = "signForm_header_elements__buttons_link"){
                                            attrs.href = "#"
                                            + "Войти"
                                            attrs.onClickFunction = {setType("signIn")}
                                        }
                                        a (classes = "signForm_header_elements__buttons_link") {
                                            attrs.href = "#"
                                            + "Регистрация"
                                            attrs.onClickFunction = {setType("signUp")}
                                        }
                                        div("signForm_header_elements__close") {
                                            +"Закрыть"
                                            attrs.onClickFunction = props.toggleSignForm
                                        }
                                    }
                                }
                            }
                            hr {  }


                            if (type == "signIn") {
                                div("signForm_body") {
                                    form(classes = "signForm_body_form") {
                                        div("signForm_body_form__input-group") {
                                            input(classes = "signForm_body_form__input-group_input") {
                                                attrs.value = username
                                                attrs.onChangeFunction = {
                                                    val target = it.target as HTMLInputElement
                                                    setUsername(target.value)
                                                }
                                                attrs.required = true
                                                attrs.placeholder = "Имя пользователя"
                                            }
                                            input(classes = "signForm_body_form__input-group_input") {
                                                attrs.value = password
                                                attrs.onChangeFunction = {
                                                    val target = it.target as HTMLInputElement
                                                    setPassword(target.value)
                                                }
                                                attrs.required = true
                                                attrs.placeholder = "Пароль"
                                                attrs.type = InputType.password
                                            }
                                        }
                                        button(classes = "signForm_body_form_btn") {
                                            +"Войти"
                                        }
                                    }
                                }
                            }

                            if (type == "signUp") {
                                div("signForm_body") {
                                    form (classes = "signForm_body_form") {
                                        div("signForm_body_form__input-group") {
                                            input(classes = "signForm_body_form__input-group_input") {
                                                attrs.value = username
                                                attrs.onChangeFunction = {
                                                    val target = it.target as HTMLInputElement
                                                    setUsername(target.value)
                                                }
                                                attrs.required = true
                                                attrs.placeholder = "Имя пользователя"
                                            }
                                            input(classes = "signForm_body_form__input-group_input") {
                                                attrs.value = password
                                                attrs.onChangeFunction = {
                                                    val target = it.target as HTMLInputElement
                                                    setPassword(target.value)
                                                }
                                                attrs.required = true
                                                attrs.placeholder = "Пароль"
                                                attrs.type = InputType.password
                                            }
                                        }
                                        button(classes = "signForm_body_form_btn") {
                                            +"Регистрация"
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

fun RBuilder.signform(
    toggleSignForm: (Event) -> Unit
) = child(
    withDisplayName("Lesson", fSignForm)
) {
    attrs.toggleSignForm = toggleSignForm
}