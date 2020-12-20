package component
import data.*
import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.dom.div
import react.dom.main
import redux.RAction
import redux.Store
import redux.WrapperAction

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
    var store: Store<State, RAction, WrapperAction>
}



fun fApp() =
    functionalComponent<AppProps> { props ->


        val (signFormOpen, setSignFormOpen) = useState(false);
        fun toggleSignForm (): (Event) -> Unit = { _: Event->
            setSignFormOpen(!signFormOpen)
        }


        main {
            navbar (toggleSignForm(), props.store)
            if(props.store.getState().user==null) {
                welcomepage()
                if(signFormOpen) {
                    signform(toggleSignForm(), props.store)
                }
            }
            else {
                mainpage(props.store)
            }
        }
    }


fun RBuilder.app(
        store: Store<State, RAction, WrapperAction>
) =
        child(
                withDisplayName("App", fApp())
        ) {
            attrs.store = store
        }