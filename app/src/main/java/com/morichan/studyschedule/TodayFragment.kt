package com.morichan.studyschedule

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_today.*


class TodayFragment : Fragment() {

    val realm: Realm = Realm.getDefaultInstance()


    var task: MutableList<TaskCreate> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_today, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RecyclerViewAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(activity)


        recyclerView.adapter = adapter
        adapter.addAll(task)

        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
        var result: RealmResults<TaskCreate> = query.findAll()

        for (items in result){
            task.add(TaskCreate(items.title))
        }
        adapter.addAll(task)
        recyclerView.adapter = adapter




        taskplus.setOnClickListener {


            val intent = Intent(activity, TodayListCreateActivity::class.java)
            startActivity(intent)


        }





    }

    override fun onResume() {
        super.onResume()

        val adapter = RecyclerViewAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(activity)

        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
        var result: RealmResults<TaskCreate> = query.findAll()

        task.clear()

        for (items in result){
            task.add(TaskCreate(items.title))
        }
        adapter.addAll(task)
        recyclerView.adapter = adapter






    }






    companion object {

    }

}

