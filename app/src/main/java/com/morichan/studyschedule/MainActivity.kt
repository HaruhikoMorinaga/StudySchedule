package com.morichan.studyschedule

import GraphFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val fragmentlist: ArrayList<Fragment> = ArrayList<Fragment>()


        val graphFragment = GraphFragment()
        val todayFragment = TodayFragment()
        val tomorrowFragment = TomorrowFragment()
        val deleteFragment = DeleteFragment()


        fragmentlist.add(todayFragment)
        fragmentlist.add(tomorrowFragment)
        fragmentlist.add(deleteFragment)
        fragmentlist.add(graphFragment)



        val tabLayout = findViewById<TabLayout>(R.id.tablayout)

        val fragmentAdapter = FragmentAdapter(this, fragmentlist)

        var tomorrow :String = "明日"

        val tabConfigurationStrategy =
            TabConfigurationStrategy { tab, position ->
                if (position == 0) {
                    tab.text = "今日"
                } else if (position == 1) {
                    tab.text = tomorrow


                } else if (position == 2) {
                    tab.text = "削除履歴"
                } else if (position ==3){
                    tab.text = "記録"
                }
            }
        pager.setAdapter(fragmentAdapter)
        TabLayoutMediator(tabLayout, pager, tabConfigurationStrategy).attach()




    }

    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener){

    }
}

