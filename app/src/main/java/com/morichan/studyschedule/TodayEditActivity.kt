package com.morichan.studyschedule

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import io.realm.Realm
import io.realm.RealmQuery
import kotlinx.android.synthetic.main.activity_today_edit.*
import kotlinx.android.synthetic.main.activity_today_edit.radioButtontoday
import kotlinx.android.synthetic.main.activity_today_edit.radioButtontomorrow
import kotlinx.android.synthetic.main.activity_today_edit.saveButton
import kotlinx.android.synthetic.main.activity_today_list_create.*
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class TodayEditActivity : AppCompatActivity() {

    var stringtitle  =""
    var stringcheckradio =""

    var seekBarint = 0
    var seekBarmaxInt = 0

    var complete = false
    var delete = false

    @RequiresApi(Build.VERSION_CODES.O)
    var localDateTime = LocalDateTime.now()

    val realm : Realm = Realm.getDefaultInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_edit)


         stringtitle = intent.getStringExtra("titletask")
         stringcheckradio = intent.getStringExtra("radiochecktask")

        var seekBarrealm = realm.where(TaskCreate::class.java).equalTo("title",stringtitle)
            .equalTo("complete",false)
            .findFirst()

      if (seekBarrealm!!.seekBarprogress != null) {
            seekBarint = seekBarrealm!!.seekBarprogress
        }


        if (seekBarrealm.calendarHour<localDateTime.hour&&
                seekBarrealm.radioButtoncheck !=="tomorrow"){
            titleTextView.setTextColor(Color.parseColor("#ff4500"))
        }
        if (seekBarrealm.calendarHour==localDateTime.hour&&
                seekBarrealm.calendarMinute<localDateTime.minute&&
                    seekBarrealm.radioButtoncheck !=="tomorrow"
           ){
            titleTextView.setTextColor(Color.parseColor("#ff4500"))
        }
        if (seekBarrealm.calendarDate<localDateTime.dayOfYear&&
                seekBarrealm.radioButtoncheck !== "tomorrow"){

            titleTextView.setTextColor(Color.parseColor("#ff4500"))


        }
        else{
            titleTextView.setTextColor(Color.parseColor("#000000"))

        }
            seekBarmaxInt = seekBarrealm.seekBarMaxInt

        progressBar.max = seekBarmaxInt
        progressBar.progress = seekBarint

        titleTextView.text = stringtitle

        if (stringcheckradio.equals("today")){
            radioButtontoday.isChecked = true
        }
        else if (stringcheckradio.equals("tomorrow")){
            radioButtontomorrow.isChecked = true
        }
        else if (stringcheckradio.equals("everyday")){
            radioButtonEveryday.isChecked = true
        }


        saveButton.setOnClickListener {
            if (seekBarint==seekBarmaxInt){
                complete = true

                var result = realm.where(TaskCreate::class.java)
                    .equalTo("title",stringtitle).findAll()
                realm.executeTransaction {
                    result.deleteAllFromRealm()

                    finish()
                }


            }

            if (radioButtontoday.isChecked ==true){
                stringcheckradio = "today"
            }
            else if(radioButtontomorrow.isChecked ==true) {
                stringcheckradio = "tomorrow"
            }
            else if (radioButtonEveryday.isChecked==true){
                stringcheckradio = "everyday"
            }

            seekBarint = progressBar.progress


            update(title=stringtitle,radioButtonString = stringcheckradio,
                  seekBarint = seekBarint,complete = complete)

            Log.d("complete",complete.toString())

            finish()
        }





}
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    fun read(): TaskCreate?{
        return realm.where(TaskCreate::class.java).findFirst()


    }




        fun update(title:String, radioButtonString:String,seekBarint:Int,complete: Boolean){


            realm.executeTransaction {
                var string = realm.where(TaskCreate::class.java).equalTo("title",title).findFirst()
                string!!.title = title
                string.radioButtoncheck = radioButtonString
                string.seekBarprogress = seekBarint
                string.complete = complete



            }
        }


    }

