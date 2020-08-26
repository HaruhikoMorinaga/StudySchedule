package com.morichan.studyschedule

import io.realm.RealmObject

open class TaskCreate(
   open var title: String = "",
   open var seekBarprogress: Int = 0
)
   :RealmObject()


