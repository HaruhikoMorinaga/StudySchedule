package com.morichan.studyschedule

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import java.util.*


class TimePick : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    var realm : Realm = Realm.getDefaultInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(
            activity,
            // in order to return parameters to MainActivity
            activity as TodayListCreateActivity,
            hour,
            minute,
            true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        //
    }

//    override fun onDestroy() {
//        super.onDestroy()
//
//            realm.executeTransaction{
//
//                val newMemo: TaskCreate = it.createObject(TaskCreate::class.java)
//                newMemo.title = title
//                newMemo.radioButtoncheck = radioButtonString
//
//            }


    }
