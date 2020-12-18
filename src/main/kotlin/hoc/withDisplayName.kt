package hoc

import react.*
import kotlin.reflect.KClass
import kotlinext.js.*

fun <P : RProps, S : RState, R : RComponent<P, S>> withDisplayName(
    name: String, kClass: KClass<R>
): KClass<R> =
    kClass.apply {
        rClass.displayName = name
    }

fun <P : RProps> withDisplayName(
    name: String, fComp: FunctionalComponent<P>
): FunctionalComponent<P> =
    Object.assign(fComp, js {
        displayName = name
    })

