package com.morichan.studyschedule

import android.icu.util.Calendar
import android.icu.util.JapaneseCalendar
import android.icu.util.TimeZone
import android.os.Build
import androidx.annotation.RequiresApi
import io.realm.RealmObject
import java.sql.Time
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*
import java.util.logging.Level.parse

//import java.util.GregorianCalendar

open class TaskCreate @RequiresApi(Build.VERSION_CODES.N) constructor(
    open var title: String = "",
    open var radioButtoncheck:String = "",
    open var seekBarprogress: Int = 0,

    open var  everyday: Boolean = false
)   :RealmObject()

