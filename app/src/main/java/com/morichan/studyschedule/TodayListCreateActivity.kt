package com.morichan.studyschedule

import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_today_list_create.*
import kotlinx.android.synthetic.main.fragment_today.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

class TodayListCreateActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    val realm :Realm= Realm.getDefaultInstance()

    var radioButtonString :String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_list_create)

        var timenow = LocalTime.now()

        radioGroup.clearCheck()






            saveButton.setOnClickListener {
                val title: String = titleEditText.text.toString()
                val radiochecktoday = radioButtontoday.isChecked
                val radiochecktomorrow = radioButtontomorrow.isChecked

                if (radioButtontoday.isChecked == true){
                    radioButtonString = "today"
                }
                else if (radioButtontomorrow.isChecked == true){
                    radioButtonString = "tomorrow"
                }



                save(title,radioButtonString)

                finish()

            }



            val memo: TaskCreate? = read()





    }



    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun read(): TaskCreate?{
        return realm.where(TaskCreate::class.java).findFirst()
    }
    fun save(title:String,radioButtonString: String){
        val memo: TaskCreate?=read()

        this.radioButtonString = radioButtonString



        realm.executeTransaction{

                val newMemo: TaskCreate = it.createObject(TaskCreate::class.java)
                newMemo.title = title
                newMemo.radioButtoncheck = radioButtonString

            }
        }

     override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

        val str = String.format(Locale.US, "%d:%d", hourOfDay, minute)

        textView.text = str




    }


    fun timepickButton(v: View) {
        val newFragment = TimePick()
        newFragment.show(supportFragmentManager, "timePicker")

    }
}







