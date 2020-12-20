package component

import data.Reply
import data.Resume
import data.State
import data.User
import hoc.withDisplayName
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.classes
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import redux.RAction
import redux.Store
import redux.WrapperAction

interface CabinetProps : RProps {
    var store: Store<State, RAction, WrapperAction>
}

private val scope = MainScope()
val userClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getUserResumeList(id:Int): List<Resume> {
    return resumesClient.get("http://localhost:3000/user/$id/resumes")
}

suspend fun deleteResume(id:Int): HttpStatusCode {
    val resp =  resumesClient.delete<HttpStatusCode>("http://localhost:3000/user/resumes/$id")
    console.log(resp)
    return resp
}


suspend fun getRepliesList(id:Int): List<Reply> {
    return resumesClient.get("http://localhost:3000/user/$id/resumes/allReplies")
}


val fCabinet =
        functionalComponent<CabinetProps> { props->
            val (resumes,setResumes) = useState(emptyList<Resume>())
            val (newResume, setNewResume) = useState(false)
            val (replies,setReplies) = useState(listOf<Reply>())
            val (title, setTitle) = useState("")
            val (desc, setDesc) = useState("")
            useEffect(dependencies = listOf()) {
                scope.launch {
                    val response = getUserResumeList(props.store.getState().user!!.id)
                    val reps = getRepliesList(props.store.getState().user!!.id)
                    setResumes(response)
                    setReplies(reps)
                }
            }

            suspend fun postResume(userId: Int): HttpStatusCode {
                val response = resumesClient.post<HttpStatusCode> {
                    url("http://localhost:3000/user/resumes")
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                    body = Resume(
                            0,
                            userId,
                            title,
                            desc
                    )
                }
                console.log(response)
//                resumesClient.close()
                return response
            }




            if(newResume) {
                div("change") {
                    h4("change_name") {+"Новое резюме"}
                    div("change_inputGroup") {
                        div("change_container") {
                            label("change_label") { +"Заголовок" }
                            input(classes = "change_input") {
                                attrs.value = title
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setTitle(target.value)
                                }
                                attrs.required = true
                            }
                        }
                        div("change_container") {
                            label("change_label") { +"Описание" }
                            input(classes = "change_input") {
                                attrs.value = desc
                                attrs.onChangeFunction = {
                                    val target = it.target as HTMLInputElement
                                    setDesc(target.value)
                                }
                                attrs.required = true
                            }
                        }
                        div("change_container") {
                            button(classes = "change_button") {
                                attrs.onClickFunction = {_:Event->
                                    scope.launch {
                                        postResume(props.store.getState().user!!.id)
                                        val response = getUserResumeList(props.store.getState().user!!.id)
                                        val reps = getRepliesList(props.store.getState().user!!.id)
                                        setResumes(response)
                                        setReplies(reps)
                                        setNewResume(false)
                                    }

                                }
                                +"Создать резюме" }
                        }
                    }
                }
            }

            else {
                div("cards") {
                    div("desccard") {
                        h1 { +"Ваши резюме" }
                        button(classes = "descbtn") { +"Создать новое резюме"
                        attrs.onClickFunction = {
                         setNewResume(true)
                            }
                        }
                    }
                    resumes.map {resume->
                        div("container center") {
                            div("card") {
                                h2("cardh2") {
                                    +resume.title
                                }
                                hr("card-hr") { }
                                p("card-p") { +resume.description }

                                h4 {
                                    +"Откликов: ${replies.filter { it.resumeId == resume.id }.size}"
                                }
                                button(classes = "cardbtn") {
                                    +"Удалить резюме"
                                    attrs.onClickFunction = {_:Event->
                                        scope.launch {
                                            deleteResume(resume.id)
                                            val response = getUserResumeList(props.store.getState().user!!.id)
                                            val reps = getRepliesList(props.store.getState().user!!.id)
                                            setResumes(response)
                                            setReplies(reps)
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }


        }

fun RBuilder.cabinet(
        store: Store<State, RAction, WrapperAction>
) = child(
        withDisplayName("Lesson", fCabinet)
) {
    attrs.store = store
}