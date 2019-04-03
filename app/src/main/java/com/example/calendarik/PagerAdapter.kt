package com.example.calendarik

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(pos: Int): Fragment {
        return CalendarFragment.getInstance(pos)
    }

    override fun getCount(): Int = Int.MAX_VALUE

}