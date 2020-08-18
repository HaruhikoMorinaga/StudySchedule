package com.morichan.studyschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
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

        recyclerView.layoutManager = LinearLayoutManager(activity)

        val list = Array<String>(10) { "テキスト$it" }
        val adapter = CustomAdapter(list)
        val layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        // インターフェースの実装
        adapter.setOnItemClickListener(
            object : CustomAdapter.OnItemClickListener {
                override fun onItemClickListener(view: View, position: Int, clickedText: String) {

//                    val intent = Intent(requireContext(), TodayEditActivity::class.java)
//                    startActivity(intent)
                }
            })

        taskplus.setOnClickListener {
            val intent = Intent(activity,TodayListCreateActivity::class.java)
            startActivity(intent)
        }




    }


    override fun onResume() {
        super.onResume()


        var query: RealmQuery<TaskCreate> = realm.where(TaskCreate::class.java)
        var result: RealmResults<TaskCreate> = query.findAll()


    }
}

