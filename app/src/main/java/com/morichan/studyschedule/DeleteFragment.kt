package com.morichan.studyschedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_delete.*
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.fragment_today.recyclerView

class DeleteFragment : Fragment() {

    val realm :Realm = Realm.getDefaultInstance()

    var titleTask = mutableListOf<String>()

    var task = mutableListOf<TaskCreate>()
    var deadlinetask = mutableListOf<String>()
    var maxIntTask = mutableListOf<Int>()
    val adapter = CustomAdapter(titleTask,deadlinetask)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onResume() {
        super.onResume()

        var query :RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
            .equalTo("deleteBoolean",true)
        var result :RealmResults<TaskCreate> = query.findAll()

        titleTask.clear()
        task.clear()
        deadlinetask.clear()
        maxIntTask.clear()



        for (item in result){

            titleTask.add(item.title)
            maxIntTask.add(item.seekBarMaxInt)
            deadlinetask.add(item.calendarHour.toString()+"時"+item.calendarMinute.toString()+"分まで")

        }

       var  numbersum = maxIntTask.sum()

        var textnumberhour = numbersum/2

        if (numbersum%2 == 0){
            deleteTextView.text  = textnumberhour.toString()+"時間サボりました"
        }
        else{

        deleteTextView.text = textnumberhour.toString()+"時間30分サボりました"

        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)


        recyclerView.adapter = adapter


    }

}