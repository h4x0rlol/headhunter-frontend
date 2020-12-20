package redux

import data.User

class UserChange(val user: User?) : RAction
class Cabinet(val cabinet: Boolean): RAction