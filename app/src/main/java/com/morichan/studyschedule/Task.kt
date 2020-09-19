package com.morichan.studyschedule

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
//import androidx.annotation.RequiresApi
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.time.LocalTime
import java.util.*


open class TaskCreate @RequiresApi(Build.VERSION_CODES.O) constructor(

    open var title: String = "",
    open var radioButtoncheck:String = "",
    open var seekBarprogress: Int = 0,
    open var seekBarMaxInt : Int =0,
    open var calendarHour: Int = Calendar.HOUR_OF_DAY,
    open var calendarMinute : Int = Calendar.MINUTE,
    open var calendarDate :Int = Calendar.DAY_OF_YEAR,
    open var complete : Boolean = false



)   :RealmObject()

