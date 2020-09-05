package com.morichan.studyschedule

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_today_list_create.*
import java.time.LocalDate
import java.util.*


class TimePick : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    var realm : Realm = Realm.getDefaultInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)


        return TimePickerDialog(
            activity,
            activity as TodayListCreateActivity,
            hour,
            minute,
            true)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {


    }}


