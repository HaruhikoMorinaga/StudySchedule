package com.morichan.studyschedule


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_today_edit.view.*
import kotlinx.android.synthetic.main.item_task.view.*


 open class CustomAdapter(private val task: MutableList<String>,private val deadlinetask: MutableList<String>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>(){


    lateinit var listener: OnItemClickListener

     // ViewHolderクラス(別ファイルに書いてもOK)
    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val titleView = view.textView2
         val deadlineview = view.deadline
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


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.textView2.text = task[position].toString()
        holder.view.deadline.text = deadlinetask[position].toString()

        // タップしたとき
        holder.view.setOnClickListener {
            listener.onItemClickListener(it, position,task[position].toString())
        }

    }

    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int,clickedText:String)


        }

    // リスナー

     fun setOnItemClickListener(listener: OnItemClickListener){
         this.listener = listener
     }



}
