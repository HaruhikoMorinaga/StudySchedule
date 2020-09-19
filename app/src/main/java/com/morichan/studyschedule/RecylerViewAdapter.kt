package com.morichan.studyschedule


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_task.view.*
import java.time.LocalTime
import java.util.*


open class CustomAdapter(
                           private val task: OrderedRealmCollection<TaskCreate>
                          ,private var listener: OnItemClickListener
                          ,private val autoUpdate :Boolean) :
     RealmRecyclerViewAdapter<TaskCreate,CustomAdapter.CustomViewHolder>(
         task,autoUpdate


     ){

     val realm = Realm.getDefaultInstance()
     @RequiresApi(Build.VERSION_CODES.O)

     var calendarNow:Int = Calendar.HOUR_OF_DAY
    var calendarNowminute = Calendar.MINUTE
    var calendarnowDay = Calendar.DAY_OF_YEAR
     // ViewHolderクラス(別ファイルに書いてもOK)
    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val titleView = view.textView2
         val deadlineview = view.deadline
         val itemseekBar = view.itemseekBar
         val progressButton = view.progressChangeButton

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.item_task, parent, false)
        return CustomViewHolder(item)
    }

    // recyclerViewのコンテンツのサイズ
    override fun getItemCount(): Int {
        return task.size
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val task:TaskCreate = task.get(position)?:return
        holder.view.textView2.text = task.title.toString()
        holder.view.deadline.text =
            task.calendarHour.toString()+"時"+task.calendarMinute.toString()+"分まで"
        holder.view.progressChangeButton.text = "保存"

        val seekBarRealm = readAll()

        for (item in seekBarRealm){
            holder.itemseekBar.max= item.seekBarMaxInt
            holder.itemseekBar.progress = item.seekBarprogress
        }


        val deadlineRealm =deadlinereadAll()
        for (item in deadlineRealm){
            if (item.calendarHour<calendarNow){
                holder.view.textView2.setTextColor(Color.parseColor("#ff0000"))
                holder.view.deadline.setTextColor(Color.parseColor("#ff0000"))
            }
            else{
                holder.view.textView2.setTextColor(Color.parseColor("#000000"))
                holder.view.deadline.setTextColor(Color.parseColor("#000000"))

            }
        }



        holder.view.setOnClickListener {
            listener.onItemClickListener(task)

//            holder.view.itemseekBar.visibility = View.VISIBLE

        }
    }

     interface OnItemClickListener {
         fun onItemClickListener(item: TaskCreate)
     }

    fun readAll():RealmResults<TaskCreate>{
        return realm.where(TaskCreate::class.java).findAll()
    }

     fun deadlinereadAll(): RealmResults<TaskCreate> {
         return realm.where(TaskCreate::class.java)
             .equalTo("radioButtoncheck","today")
             .or().equalTo("radioButtoncheck","everyday")
             .findAll()
     }





}
