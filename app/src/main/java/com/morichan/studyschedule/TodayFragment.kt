package com.morichan.studyschedule

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_today.*
import java.time.LocalDate


class TodayFragment : Fragment() {

    val realm: Realm = Realm.getDefaultInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    var localDate  =LocalDate.now()

    var task: MutableList<TaskCreate> = mutableListOf()

    var maxInttask: MutableList<Int> = mutableListOf()
    var progressInttask: MutableList<Int> = mutableListOf()

    var maxIntGraph = 0
    var progressIntGraph = 0
    var localDatedateInt  =0



    var deadlinetask:MutableList<String> = mutableListOf()

    var titleTask :MutableList<String> = mutableListOf()

    val adapter = CustomAdapter(titleTask,deadlinetask)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_today, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.setHasFixedSize(true)
//
//        adapter.setOnItemClickListener(
//            object : CustomAdapter.OnItemClickListener {
//                override fun onItemClickListener(
//                    view: View, position: Int, clickedText: String
//                ) {
//                    val intent = Intent(requireContext(), TodayEditActivity::class.java)
//                    intent.putExtra("titletask",task[position].title)
//                    intent.putExtra("radiochecktask",task[position].radioButtoncheck)
//
//
//                    startActivity(intent)
//
//
//                }
//            })



        taskplus.setOnClickListener {
            val intent = Intent(activity, TodayListCreateActivity::class.java)
            startActivity(intent)
        }




    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()



        localDate = LocalDate.now()



        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
        query.equalTo("complete",false)
            .equalTo("radioButtoncheck", "today")
            .or().equalTo("radioButtoncheck","everyday")

        var result: RealmResults<TaskCreate> = query.findAll()





        titleTask.clear()

        deadlinetask.clear()

        maxInttask.clear()
        progressInttask.clear()

        var number = localDate.dayOfYear






        for(item in result) {
            titleTask.addAll(listOf(item.title))
            deadlinetask.add(
                item.calendarHour.toString()
                        + "時" + item.calendarMinute.toString() + "分まで"
            )

            maxInttask.add(item.seekBarMaxInt)
            progressInttask.add(item.seekBarprogress)

//            if (item.radioButtoncheck.equals("everyday")&&
//                    item.calendarDate !==number){
//                item.seekBarprogress = 0
//            }


        }

        task.addAll(result)


        maxIntGraph = maxInttask.sum()
        progressIntGraph = progressInttask.sum()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        adapter.setOnItemClickListener(
            object : CustomAdapter.OnItemClickListener {
                override fun onItemClickListener(
                    view: View, position: Int, clickedText: String
                ) {
                    val intent = Intent(requireContext(), TodayEditActivity::class.java)
                    intent.putExtra("titletask",titleTask[position])
                    intent.putExtra("radiochecktask",task[position].radioButtoncheck)


                    startActivity(intent)


                }
            })

        recyclerView.adapter = adapter



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveGraph(localDate: Int, maxInt: Int, progressInt: Int){

        realm.executeTransaction {



            val newGraphdata:Graph = it.createObject(Graph::class.java)
            newGraphdata.localDate = localDate
            newGraphdata.maxInt = maxInt
            newGraphdata.progressInt = progressInt

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()

        localDatedateInt = localDate.dayOfYear
        saveGraph(localDatedateInt,maxIntGraph,progressIntGraph)

        Log.d("aiueo",progressIntGraph.toString())
        Log.d("aiue",maxIntGraph.toString())
        Log.d("aiu",localDatedateInt.toString())

    }

}



//進捗ごとの背景色
//onitemlongclick
//seekBarをrecyclerviewで
//

