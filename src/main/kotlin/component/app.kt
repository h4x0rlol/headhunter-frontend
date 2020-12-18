package component




import data.Course
import data.*
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.*


private val scope = MainScope()
val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }

}

suspend fun getShoppingList(): List<Course> {
    return jsonClient.get("http://admin-happy-hearts.herokuapp.com/api/courses")
}

interface AppProps : RProps {
    var lessons: Map<Int, Lesson>
    var students: Map<Int, Student>
}



fun fApp() =
    functionalComponent<AppProps> { props ->
        useEffect(dependencies = listOf()) {
            scope.launch {
               val xd =  getShoppingList()
                console.log(xd)
            }
        }
      button {}
    }
