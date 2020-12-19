package component
import data.*
import org.w3c.dom.events.Event
import react.*
import react.dom.div
import react.dom.main

//
//private val scope = MainScope()
//val jsonClient = HttpClient {
//    install(JsonFeature) { serializer = KotlinxSerializer() }
//
//}

//suspend fun getShoppingList(): List<Course> {
//    return jsonClient.get("http://admin-happy-hearts.herokuapp.com/api/courses")
//}

interface AppProps : RProps {
    var user: User?
}



fun fApp() =
    functionalComponent<AppProps> { props ->


        val (signFormOpen, setSignFormOpen) = useState(false);
        fun toggleSignForm (): (Event) -> Unit = { _: Event->
            setSignFormOpen(!signFormOpen)
        }


        main {
            navbar (toggleSignForm(), props.user)
            if(props.user==null) {
                welcomepage()
                if(signFormOpen) {
                    signform(toggleSignForm())
                }
            }
            else {
                div { +"User" }
                div {+"Data"}
            }
        }
    }
