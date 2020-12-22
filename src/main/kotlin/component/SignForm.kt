package component

import data.State
import data.User
import hoc.withDisplayName
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import redux.RAction
import redux.Store
import redux.UserChange
import redux.WrapperAction

private val scope = MainScope()
val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}





interface SignFormProps : RProps {
    var toggleSignForm: (Event) -> Unit
    var store: Store<State, RAction, WrapperAction>
}

val fSignForm =
    functionalComponent<SignFormProps> {props ->
        val (type, setType) = useState("signIn")
        val (username, setUsername) = useState("")
        val (password, setPassword) = useState("")
        val (status, setStatus) = useState(0)
        val (invalidUser, setInvalidUser) = useState(false)
        val (invalidPass, setInvalidPass) = useState(false)
        val (userExist, setUserExist) = useState(false)

        suspend fun submitSignIn() {
            try {
                val newUser = jsonClient.post<User> {
                    url("http://localhost:3000/user/login")
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                    body = User(
                            0,
                            username,
                            password,
                            0
                    )
                }
                console.log(newUser)
                props.store.dispatch(UserChange(newUser))
                props.toggleSignForm
            }
            catch (e:ClientRequestException ) {
                setInvalidPass(true)
            }
            catch (e: ServerResponseException) {
                setInvalidUser(true)
            }
        }

        suspend fun submitSignUp(){
            try {
                val newUser = jsonClient.post<User> {
                    url("http://localhost:3000/user")
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                    body = User(id = 0,
                            username = username,
                            password = password,
                            status = status
                    )
                }
//                console.log(newUser)
                props.store.dispatch(UserChange(newUser))
                props.toggleSignForm
            }
            catch (e:ClientRequestException ) {
             setUserExist(true)
            }

        }

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
                                        attrs.onSubmitFunction = {
                                            it.preventDefault()
                                            scope.launch {
                                                submitSignIn()
                                            }
                                        }
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
                                            if(invalidUser) {
                                                p("invalid") { +"Такого пользователя не существует" }
                                            }
                                            if(invalidPass) {
                                                p("invalid") {+"Неверный пароль"}
                                            }
                                        }
                                        button(classes = "signForm_body_form_btn") {
                                            +"Войти"
                                            attrs.onClickFunction = {
                                                setInvalidPass(false)
                                                setInvalidUser(false)
                                            }
                                        }
                                    }
                                }
                            }

                            if (type == "signUp") {
                                div("signForm_body") {
                                    form (classes = "signForm_body_form") {
                                        attrs.onSubmitFunction = {
                                            it.preventDefault()
                                            scope.launch {
                                                submitSignUp()
                                            }
                                        }
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
                                            select(classes = "signForm_body_form__input-group_input") {
                                                option { +"Я ищу работу" }
                                                option { +"Я ищу работников" }
                                                attrs.onChangeFunction = {
                                                    val text = it.target as HTMLSelectElement
                                                    if (text.value == "Я ищу работу") {
                                                        setStatus(0)
                                                    }
                                                    else {
                                                        setStatus(1)
                                                    }
                                                }
                                            }
                                            if(userExist) {
                                                p("invalid") {+"Пользователь с таким именем уже существует"}
                                            }
                                        }
                                        button(classes = "signForm_body_form_btn") {
                                            +"Регистрация"
                                            attrs.onClickFunction = {
                                                setUserExist(false)
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
    }

fun RBuilder.signform(
    toggleSignForm: (Event) -> Unit,
    store: Store<State, RAction, WrapperAction>
) = child(
    withDisplayName("Lesson", fSignForm)
) {
    attrs.toggleSignForm = toggleSignForm
    attrs.store = store
}