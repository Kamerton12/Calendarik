package com.example.calendarik

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CalendarFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val page = arguments!!.getInt("page")
    }

    companion object {
        fun getInstance(page: Int): Fragment{
            val bundle = Bundle()
            bundle.putInt("page", page)
            val frag = CalendarFragment()
            frag.arguments = bundle
            return frag
        }
    }
}