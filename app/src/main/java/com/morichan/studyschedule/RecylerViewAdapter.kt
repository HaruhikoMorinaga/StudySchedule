package com.morichan.studyschedule


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*


 open class CustomAdapter(private val task: MutableList<TaskCreate>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>(){

    //
    lateinit var listener: OnItemClickListener

    // ViewHolderクラス(別ファイルに書いてもOK)
    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val titleView = view.textView2
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

    // ViewHolderに表示する画像とテキストを挿入
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.textView2.text = task[position].toString()
        // タップしたとき
        holder.view.setOnClickListener {
            listener.onItemClickListener(it, position, task[position].toString())
        }
    }

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int
                                , clickedText: String
        )}

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}
