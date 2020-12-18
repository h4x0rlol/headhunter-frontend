package component

import hoc.withDisplayName
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.functionalComponent

interface AnyFullProps<O, S> : RProps {
    var obj: Pair<Int, O>
    var subobjs: Map<Int, S>
    var presents: Map<Int, Boolean>?
    var onClick: (Int) -> (Event) -> Unit
}

fun <O, S> fAnyFull(
    rComponent: RBuilder.(S, String, (Event) -> Unit) -> ReactElement
) =
    functionalComponent<AnyFullProps<O, S>> { props ->
        h3 {
            +props.obj.second.toString()
        }
        ul {
            props.subobjs.map {
                val present = props.presents?.get(it.key) ?: false
                val cssClass = if (present) "present" else "absent"
                li {
                    rComponent(it.value, cssClass, props.onClick(it.key))
                }
            }
        }
    }

fun <O, S> RBuilder.anyFull(
    rComponent: RBuilder.(S, String, (Event) -> Unit) -> ReactElement,
    obj: Pair<Int, O>,
    subobjs: Map<Int, S>,
    presents: Map<Int, Boolean>?,
    onClick: (Int) -> (Event) -> Unit
) = child(
    withDisplayName("Full", fAnyFull<O, S>(rComponent))
) {
    attrs.obj = obj
    attrs.subobjs = subobjs
    attrs.presents = presents
    attrs.onClick = onClick
}