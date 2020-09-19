package com.morichan.studyschedule

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.item_task.view.*
import java.time.LocalDateTime
import java.time.LocalTime


class TodayFragment : Fragment()
{

    val realm: Realm = Realm.getDefaultInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    var localDate  =LocalDateTime.now()

    var task: MutableList<TaskCreate> = mutableListOf()

    var maxInttask: MutableList<Int> = mutableListOf()
    var progressInttask: MutableList<Int> = mutableListOf()

//    var maxIntGraph = 0
//    var progressIntGraph = 0
//    var localDatedateInt  =0

    var titleTask = readAll()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_today, container, false)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskplus.setOnClickListener {
            val intent = Intent(activity, TodayListCreateActivity::class.java)
            startActivity(intent)
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()



        localDate = LocalDateTime.now()
        var localTime = LocalTime.now()

//        datetextview.text = localDate.monthValue.toString()+"月"+localDate.dayOfMonth.toString()+"日"


        var tomorrowquery: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
            .equalTo("radioButtoncheck", "tomorrow").equalTo("complete", false)

        var tomorrowresult = tomorrowquery.findAll()

        for (item in tomorrowresult) {
            if (item.calendarDate == localDate.dayOfYear) {
                realm.executeTransaction {
                    item.radioButtoncheck = "today"
                }
            }
        }

        var everyDayQuery = realm.where(TaskCreate::class.java)
            .equalTo("radioButtoncheck", "everyday").equalTo("complete", false)
        var everyDayresult = everyDayQuery.findAll()

        for (item in everyDayresult) {
            if (item.calendarDate != localDate.dayOfYear) {
                realm.executeTransaction {
                    item.seekBarprogress = 0
                }
            }
        }


        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
        query.equalTo("complete", false)
            .equalTo("radioButtoncheck", "today")
            .or().equalTo("radioButtoncheck", "everyday")


        var result: RealmResults<TaskCreate> = query.findAll()




        maxInttask.clear()
        progressInttask.clear()

        var number = localDate.dayOfYear

        for (item in result) {

            maxInttask.add(item.seekBarMaxInt)
            progressInttask.add(item.seekBarprogress)


//            if (item.radioButtoncheck.equals("everyday")&&
//                    item.calendarDate !==number){
//                item.seekBarprogress = 0
//            }


        }
        titleTask = readAll()

        task.addAll(result)

        if (result != null) {
            for (item in result) {
                if (localDate.dayOfYear != item.calendarDate) {

                    Log.d("result", result.size.toString())


                }
            }
        }


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)


        val adapter =
            CustomAdapter(titleTask, object : CustomAdapter.OnItemClickListener {


                override fun onItemClickListener(item: TaskCreate) {

                    view!!.itemseekBar.visibility = View.VISIBLE
                    view!!.progressChangeButton.visibility = View.VISIBLE
                    view!!.textView2.visibility = View.INVISIBLE
                    view!!.deadline.visibility = View.INVISIBLE


                    view!!.progressChangeButton.setOnClickListener {
                        view!!.itemseekBar.visibility = View.INVISIBLE
                        view!!.progressChangeButton.visibility = View.INVISIBLE
                        view!!.textView2.visibility = View.VISIBLE
                        view!!.deadline.visibility = View.VISIBLE
                        var progressInt = view!!.itemseekBar.progress
                        var complete = false
                        if (progressInt == view!!.itemseekBar.max) {
                            complete = true

                            Toast.makeText(requireContext(),"達成しました",Toast.LENGTH_SHORT)

                        }

                        update(item.title, progressInt, complete = complete)

                    }


                }
            }, true)



        recyclerView.adapter = adapter

//        val itemTouchHelper = ItemTouchHelper(
//            object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
//
//
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                Toast.makeText(requireContext(),"ドラック＆ドロップはできません", LENGTH_LONG).show()
//                return true
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//
//                var swipequery:RealmQuery<TaskCreate> =
//                    realm.where(TaskCreate::class.java).equalTo("title",titleTask[])
//
//        var swiperesult = swipequery.findFirst()
//
//        realm.executeTransaction {
//            }
//
//        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
//        query.equalTo("complete",false)
//            .equalTo("deleteBoolean",false)
//            .equalTo("radioButtoncheck", "today")
//            .or().equalTo("radioButtoncheck","everyday")
//
//
//        var result: RealmResults<TaskCreate> = query.findAll()
//
//        titleTask.clear()
//
//        for (item in result) {
//            titleTask.add(item.title)
//        }
//
//        recyclerView.adapter = adapter
//
//
//
//                Snackbar.make(view!!, "アイテムを削除しました", Snackbar.LENGTH_LONG)
//                    .setAction("元に戻す") {
//                        realm.executeTransaction {
//                        swiperesult?.deleteBoolean = false}
//                    }
//                    .show()
//
//                var  snackquery: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
//                query.equalTo("complete",false)
//                    .equalTo("deleteBoolean",false)
//                    .equalTo("radioButtoncheck", "today")
//                    .or().equalTo("radioButtoncheck","everyday")
//
//
//                var snackresult: RealmResults<TaskCreate> = snackquery.findAll()
//
//                titleTask.clear()
//
//                for (item in snackresult) {
//                    titleTask.add(item.title)
//                }
//
//
//
//                recyclerView.adapter = adapter
//
//
//
//            }
//        })
//        itemTouchHelper.attachToRecyclerView(recyclerView)}
//
//
//
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun read(){
        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
//        query.equalTo("complete",false)
            .equalTo("radioButtoncheck", "today")
            .or().equalTo("radioButtoncheck","everyday")


        var result: RealmResults<TaskCreate> = query.findAll()

        titleTask.clear()




        maxInttask.clear()
        progressInttask.clear()

        var number = localDate.dayOfYear

        for(item in result) {

            maxInttask.add(item.seekBarMaxInt)
            progressInttask.add(item.seekBarprogress)

//            if (item.radioButtoncheck.equals("everyday")&&
//                    item.calendarDate !==number){
//                item.seekBarprogress = 0
//            }


        }

        task.addAll(result)

    }
    fun readAll(): RealmResults<TaskCreate> {
        return realm.where(TaskCreate::class.java)
            .equalTo("complete",false)
            .equalTo("radioButtoncheck","today")
            .or().equalTo("radioButtoncheck","everyday").findAll()
    }

    fun update(title:String,seekBarint:Int,complete: Boolean){


        realm.executeTransaction {
            var string = realm.where(TaskCreate::class.java).equalTo("title",title).findFirst()

            string!!.seekBarprogress = seekBarint
            string.complete = complete



        }
    }



}



//進捗ごとの背景色
//onitemlongclick
//seekBarをrecyclerviewで
//

