package com.morichan.studyschedule

import android.content.Intent
import android.graphics.Color
import android.media.MediaRouter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.utils.Utils
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_today_edit.*
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.item_task.*
import java.time.LocalDate
import java.time.LocalTime


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

    var delete = false


    var titleposition = 0

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
        var localTime = LocalTime.now()

        datetextview.text = localDate.monthValue.toString()+"月"+localDate.dayOfMonth.toString()+"日"


        var tomorrowquery :RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
            .equalTo("radioButtoncheck","tomorrow").equalTo("complete",false)

        var tomorrowresult = tomorrowquery.findAll()

        for (item in tomorrowresult){
            if ( item.calendarDate == localDate.dayOfYear){
                realm.executeTransaction {
                item.radioButtoncheck = "today"}
            }}

        var everyDayQuery = realm.where(TaskCreate::class.java)
            .equalTo("radioButtoncheck","everyday").equalTo("complete",false)
        var everyDayresult = everyDayQuery.findAll()

        for (item in everyDayresult){
            if (item.calendarDate != localDate.dayOfYear){
                realm.executeTransaction {
                item.seekBarprogress = 0}
            }
        }



        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
        query.equalTo("complete",false)
            .equalTo("deleteBoolean",false)
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
                    titleposition = position
                    val intent = Intent(requireContext(), TodayEditActivity::class.java)
                    intent.putExtra("titletask",titleTask[position])
                    intent.putExtra("radiochecktask",task[position].radioButtoncheck)


                    startActivity(intent)


                }
            })

        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                var swipequery = realm.where(TaskCreate::class.java)
                    .equalTo("title",titleTask[titleposition])

                var swiperesult = swipequery.findFirst()
                realm.executeTransaction {
                swiperesult!!.deleteBoolean  = true}

                var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
                query.equalTo("complete",false)
                    .equalTo("deleteBoolean",false)
                    .equalTo("radioButtoncheck", "today")
                    .or().equalTo("radioButtoncheck","everyday")


                var result: RealmResults<TaskCreate> = query.findAll()

                titleTask.clear()

                for (item in result) {
                    titleTask.add(item.title)
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)}

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun read(){
        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
        query.equalTo("complete",false)
            .equalTo("deleteBoolean",false)
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
    }

}



//進捗ごとの背景色
//onitemlongclick
//seekBarをrecyclerviewで
//

