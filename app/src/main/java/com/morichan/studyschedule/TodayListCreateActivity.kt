package com.morichan.studyschedule

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.core.view.isVisible
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_today_list_create.*

class TodayListCreateActivity : AppCompatActivity() {

    val realm :Realm= Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_list_create)

            saveButton.setOnClickListener {
                val title: String = titleEditText.text.toString()

                save(title)

                finish()

            }
            saveButton.isClickable = false

        if (!titleEditText.text.equals(null)) {
            saveButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#000080"))
            saveButton.isClickable = true
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
    fun save(title:String){
        val memo: TaskCreate?=read()

        realm.executeTransaction{

                val newMemo: TaskCreate = it.createObject(TaskCreate::class.java)
                newMemo.title = title

            }
        }



    }


