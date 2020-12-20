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
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import redux.RAction
import redux.Store
import redux.UserChange
import redux.WrapperAction

interface MainPageProps : RProps {
    var store: Store<State, RAction, WrapperAction>
}

private val scope = MainScope()
val resumesClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun getResumeList(): List<Resume> {
    return resumesClient.get("http://localhost:3000/user/resumes")
}

suspend fun getReplyList(id: Int): List<Boolean> {
    return resumesClient.get("http://localhost:3000/user/$id/resumes/replies")
}

suspend fun postReply(userId: Int, resumeId: Int ): HttpStatusCode {
    val newReply = resumesClient.post<HttpStatusCode> {
        url("http://localhost:3000/user/resumes/reply")
        contentType(ContentType.Application.Json)
        accept(ContentType.Application.Json)
        body = Reply(
                id = 0,
                resumeId = resumeId,
                userId = userId
        )
    }
    console.log(newReply)
    resumesClient.close()
    return newReply
}

suspend fun deleteReply(id: Int, resumeId: Int): HttpStatusCode {
    val reply =  resumesClient.get<Reply>("http://localhost:3000/user/$id/resumes/$resumeId/reply")
    val deleteReply =  resumesClient.delete<HttpStatusCode>("http://localhost:3000/user/resumes/reply/${reply.id}")
    console.log(deleteReply)
    return deleteReply
}

val fMainPage =
        functionalComponent<MainPageProps> {props->
            val (resumeList, setResumeList) = useState(emptyList<Resume>())
            val (replyList, setReplyList) = useState(MutableList(10){false})
            useEffect(dependencies = listOf(replyList)) {
                scope.launch {
                    val resumes = getResumeList()
                    val replies = getReplyList(props.store.getState().user!!.id)
                    setResumeList(resumes)
                    setReplyList(replies.toMutableList())
//                    console.log(replies)
                }
            }


            if(props.store.getState().cabinet) {
                cabinet(props.store)
            }
            else {
                div("cards") {
                    resumeList.mapIndexed { index, resume ->
                        div("container center") {
                            div("card") {
                                h2("cardh2") {
                                    +resume.title
                                }
                                hr("card-hr") { }
                                p("card-p") { +resume.description }
                                if (props.store.getState().user!!.status == 1 && props.store.getState().user!!.id !=resume.userId) {
                                    if(replyList[index]) {
                                        button(classes = "cardbtn") {
                                            attrs.onClickFunction = {_:Event->
                                                scope.launch {
                                                    deleteReply(props.store.getState().user!!.id,resume.id)
                                                }
                                                replyList[index] = false
                                                setReplyList(replyList)
                                            }
                                            +"Удалить отклик" }
                                    }
                                    else {
                                        button(classes = "cardbtn") {
                                            attrs.onClickFunction = {_:Event->
                                                scope.launch {
                                                    postReply(props.store.getState().user!!.id,resume.id)
                                                }
                                                replyList[index] = true
                                                setReplyList(replyList)
                                            }
                                            +"Откликнуться" }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }


fun RBuilder.mainpage(
        store: Store<State, RAction, WrapperAction>
) = child(
        withDisplayName("Lesson", fMainPage)
) {
    attrs.store = store
}