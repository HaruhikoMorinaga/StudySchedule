package com.morichan.studyschedule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_today_edit.*

class TodayEditActivity : AppCompatActivity() {

    var stringtitle  =""
    var stringcheckradio =""

    var checkEveryday = false
    var seekBarint = 0

    val realm : Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_edit)



         stringtitle = intent.getStringExtra("titletask")
         stringcheckradio = intent.getStringExtra("radiobuttoncheck")

        var seekBarrealm = realm.where(TaskCreate::class.java).equalTo("title",stringtitle)
            .findFirst()

        if (seekBarrealm!!.seekBarprogress== null){
            seekBarint = 0
        }
        else {
            seekBarint = seekBarrealm!!.seekBarprogress
        }

        progressBar.progress = seekBarint



        titleTextView.text = stringtitle

        if (stringcheckradio.equals("today")){
            radioButtontoday.isChecked = true
        }
        else if (stringcheckradio.equals("tomorrow")){
            radioButtontomorrow.isChecked = true
        }


        saveButton.setOnClickListener {

            if (radioButtontoday.isChecked ==true){
                stringcheckradio = "today"
            }
            else if(radioButtontomorrow.isChecked ==true) {
                stringcheckradio = "tomorrow"
            }

            checkEveryday = everydayBox.isChecked
            seekBarint = progressBar.progress


            update(title=stringtitle,radioButtonString = stringcheckradio,
                   checkEveryday = checkEveryday,seekBarint = seekBarint)

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




        fun update(title:String, radioButtonString:String, checkEveryday: Boolean,seekBarint:Int){
            realm.executeTransaction {
                var string = realm.where(TaskCreate::class.java).equalTo("title",title).findFirst()
                string!!.title = title
                string.radioButtoncheck = radioButtonString
                string.everyday = checkEveryday
                string.seekBarprogress = seekBarint

            }
        }

    }

