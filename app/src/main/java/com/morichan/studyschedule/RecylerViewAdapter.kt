package com.morichan.studyschedule



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val context:Context) :RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    val items:MutableList<TaskCreate> = mutableListOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_task,parent,false)
        return ViewHolder(view)






    }



    override fun getItemCount(): Int {

        return items.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.titleView.text = item.title



    }
    fun addAll(items:List<TaskCreate>){
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){


        val titleView= view.findViewById<TextView>(R.id.textView2)


    }




}