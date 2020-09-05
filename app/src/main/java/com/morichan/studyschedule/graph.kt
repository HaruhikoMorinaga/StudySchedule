package com.morichan.studyschedule

import android.os.Build
import androidx.annotation.RequiresApi
import io.realm.RealmObject
import java.time.LocalDate

open class Graph @RequiresApi(Build.VERSION_CODES.O) constructor(

    open var localDate:Int = 0,
    open var maxInt :Int = 0,
    open var progressInt :Int = 0



) :RealmObject()


