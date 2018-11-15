package com.example.cosmin.recyclerviewapp.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        fun addFragmentToActivity(fragmentManager: FragmentManager,
                                  fragment: Fragment,
                                  frameId: Int,
                                  tag: String){
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(frameId, fragment, tag)
            fragmentTransaction.commit()
        }
    }
}