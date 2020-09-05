package com.morichan.studyschedule

import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_today_list_create.*
import kotlinx.android.synthetic.main.fragment_today.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import java.util.Calendar.HOUR
import java.util.Calendar.MINUTE


class TodayListCreateActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    val realm :Realm= Realm.getDefaultInstance()

    var radioButtonString :String = ""

    var spinnerposition = 0

    @RequiresApi(Build.VERSION_CODES.O)
    var localDate :LocalDate = LocalDate.now()


    @RequiresApi(Build.VERSION_CODES.O)
    var calendar : Calendar = Calendar.getInstance()

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
                var maxInt :Int = (spinnerposition+1)

                if (radioButtontoday.isChecked == true){
                    radioButtonString = "today"
                }
                else if (radioButtontomorrow.isChecked == true){
                    radioButtonString = "tomorrow"
                }
                else if(radioButtonEveryDay.isChecked==true){
                    radioButtonString = "everyday"
                }

                check(title)





                save(title, radioButtonString, maxInt,calendar[HOUR],calendar[MINUTE],localDate)

                Log.d("calendarhour",calendar[HOUR].toString())
                Log.d("calendarminute",calendar[MINUTE].toString())
                finish()

            }

        var spinneradapter :ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_dropdown_item
        )
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinneradapter.add("30分")
        spinneradapter.add("1")
        spinneradapter.add("1.5時間")
        spinneradapter.add("2時間")
        spinneradapter.add("2.5時間")
        spinneradapter.add("3時間")

        maxIntSpinner.adapter = spinneradapter

        maxIntSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                spinnerposition = position

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
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
    @RequiresApi(Build.VERSION_CODES.O)
    fun save(title: String, radioButtonString: String, maxInt: Int,
             calendarhour:Int, calendarMinute:Int, localDate: LocalDate){
        val memo: TaskCreate?=read()

        this.radioButtonString = radioButtonString




        realm.executeTransaction{

                val newMemo: TaskCreate = it.createObject(TaskCreate::class.java)
                newMemo.title = title
                newMemo.radioButtoncheck = radioButtonString
                newMemo.seekBarMaxInt = maxInt
                newMemo.calendarHour = calendarhour
                newMemo.calendarMinute = calendarMinute
               newMemo.calendarDate = localDate.dayOfYear


            }
        }

     @RequiresApi(Build.VERSION_CODES.O)
     override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

        calendar[HOUR] = hourOfDay
         calendar[MINUTE] = minute

          localDate = LocalDate.now()

         timepickbutton.text=  hourOfDay.toString()+"時"+minute.toString()+"分まで"

    }


    fun timepickButton(v: View) {
        val newFragment = TimePick()
        newFragment.show(supportFragmentManager, "timePicker")


    }

    fun check(title: String){
        var querycheck :RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
            .equalTo("title",title)
        var resultcheck :RealmResults<TaskCreate> = querycheck.findAll()
        if (resultcheck.size != 0){
            saveButton.isClickable  =false
            Toast.makeText(this,"おなじtitleのタスクがあります",Toast.LENGTH_LONG).show()

            finish()
        }
    }
}







