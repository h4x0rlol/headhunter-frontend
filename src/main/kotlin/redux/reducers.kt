package redux

import data.*


fun rootReducer(state: State, action: RAction) =
    when (action) {
        is UserChange -> State(action.user)
        else -> state
    }