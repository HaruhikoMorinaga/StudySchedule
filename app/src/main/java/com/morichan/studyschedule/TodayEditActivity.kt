package com.morichan.studyschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_today_edit.*

class TodayEditActivity : AppCompatActivity() {

    val realm : Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_edit)

//        progressBar.setProgress(realm.where())


    }
}
