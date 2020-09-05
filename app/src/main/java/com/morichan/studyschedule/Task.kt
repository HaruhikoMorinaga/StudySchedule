package com.morichan.studyschedule

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
//import androidx.annotation.RequiresApi
import io.realm.RealmObject
import java.time.LocalTime


open class TaskCreate @RequiresApi(Build.VERSION_CODES.O) constructor(
    open var title: String = "",
    open var radioButtoncheck:String = "",
    open var seekBarprogress: Int = 0,
    open var seekBarMaxInt : Int =0,
    open var calendarHour: Int = Calendar.HOUR_OF_DAY ,
    open var calendarMinute : Int = Calendar.MINUTE,
    open var calendarDate :Int = Calendar.DATE,
    open var complete : Boolean = false,
    open var deleteBoolean: Boolean = false

)   :RealmObject()

