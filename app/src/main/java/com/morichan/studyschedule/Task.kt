package com.morichan.studyschedule

import io.realm.RealmObject
import java.util.*

open class TaskCreate(
   open var title :String = ""
   )
   :RealmObject()