package com.morichan.studyschedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentlist: ArrayList<Fragment> = ArrayList<Fragment>()


        val todayFragment = TodayFragment()
        val tomorrowFragment = TomorrowFragment()


        fragmentlist.add(todayFragment)
        fragmentlist.add(tomorrowFragment)

        val tabLayout = findViewById<TabLayout>(R.id.tablayout)

        val fragmentAdapter = FragmentAdapter(this, fragmentlist)

        var tomorrow :String = "明日"





        val tabConfigurationStrategy =
            TabConfigurationStrategy { tab, position ->
                if (position == 0) {
                    tab.text = "今日"
                } else if (position == 1) {
                    tab.text = tomorrow


                }
            }
        pager.setAdapter(fragmentAdapter)
        TabLayoutMediator(tabLayout, pager, tabConfigurationStrategy).attach()

    }
}