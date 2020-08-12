package com.morichan.studyschedule

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class FragmentAdapter //
    (
    fragmentActivity: FragmentActivity?,
    var list: ArrayList<Fragment>
) :
    FragmentStateAdapter(fragmentActivity!!) {


    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

}