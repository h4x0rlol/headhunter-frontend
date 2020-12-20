import component.app
import data.*
import react.dom.render
import react.redux.provider
import react.router.dom.hashRouter
import redux.*
import kotlinx.browser.document

val store = createStore(
        ::rootReducer,
        State(user = null, cabinet = false),
        compose(
                rEnhancer(),
                applyMiddleware()
        )
)

val rootDiv =
        document.getElementById("root")

fun render() = render(rootDiv) {
    hashRouter {
        app(store)
    }
}

fun main() {
    render()
    store.subscribe {
        render()
    }
}

