import container.*
import data.*
import react.dom.render
import react.redux.provider
import react.router.dom.hashRouter
import redux.*
import kotlinx.browser.document

val store: Store<State, RAction, WrapperAction> = createStore(
    ::rootReducer,
    initialState(),
    compose(
        rEnhancer(),
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
    )
)

fun main() {
    render(document.getElementById("root")) {
        provider(store) {
            hashRouter {
                appContainer {}
            }
        }
    }
}

