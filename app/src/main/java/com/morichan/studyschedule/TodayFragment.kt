package com.morichan.studyschedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
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


    val adapter = CustomAdapter(task)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_today, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




//        val adapter = CustomAdapter(task)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())


//        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.setHasFixedSize(true)

        adapter.setOnItemClickListener(
            object : CustomAdapter.OnItemClickListener {
                override fun onItemClickListener(
                    view: View, position: Int, clickedText: String
                ) {

                    val intent = Intent(requireContext(), TodayEditActivity::class.java)
                    intent.putExtra("titletask",task[position].title)
                    intent.putExtra("radiobuttoncheck",task[position].radioButtoncheck)
                    startActivity(intent)
                }
            })

        taskplus.setOnClickListener {
            val intent = Intent(activity, TodayListCreateActivity::class.java)
            startActivity(intent)
        }




    }


    override fun onResume() {
        super.onResume()

        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java).distinct("title")
        query.equalTo("radioButtoncheck", "today")

        var result: RealmResults<TaskCreate> = query.findAll()



        task.clear()
        task.addAll(result)






        recyclerView.adapter = adapter



    }
}

