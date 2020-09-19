package com.morichan.studyschedule

import android.app.ActionBar
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.realm.Realm
import io.realm.RealmQuery
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import kotlin.math.max


class MainActivity : AppCompatActivity() {

    var pacentage : String = ""
    val realm = Realm.getDefaultInstance()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        when(item.itemId) {
            R.id.today -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, TodayFragment())
                    .commit()
                pacentage = pacentage()
                toolBar.title = "今日のtodo達成率"+ pacentage+"%"
                setSupportActionBar(toolBar)
                return@OnNavigationItemSelectedListener true


            }
            R.id.tomorrow -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, TomorrowFragment())
                    .commit()
                toolBar.title = "明日のtodo"
                setSupportActionBar(toolBar)
                return@OnNavigationItemSelectedListener true

                true
            }

            else -> false
        }}


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, TodayFragment())
            .commit()


    }

    fun pacentage():String{
        var query:RealmQuery<TaskCreate> =
            realm.where(TaskCreate::class.java)
                .equalTo("radioButtoncheck","today")
                .or().equalTo("radioButtoncheck","everyday")
        var result = query.findAll()

        var maxIntlist = mutableListOf<Int>()
        var progressIntlist = mutableListOf<Int>()
        for (item in result){
            maxIntlist.add(item.seekBarMaxInt)
            progressIntlist.add(item.seekBarprogress)
        }
        var maxInt = maxIntlist.sum()
        var progressInt = progressIntlist.sum()
//        var pacentageDecimal:BigDecimal = progressInt.toBigDecimal()/maxInt.toBigDecimal()

        var pacentageInt = progressInt*100/ maxInt
        pacentage = pacentageInt.toString()

        return pacentage
    }



    }






