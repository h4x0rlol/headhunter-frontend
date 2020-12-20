package redux

import data.*


fun rootReducer(state: State, action: RAction) =
    when (action) {
        is UserChange -> State(action.user,state.cabinet)
        is Cabinet -> State(state.user,action.cabinet)
        else -> state
    }