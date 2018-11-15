package com.example.cosmin.recyclerviewapp.list

import android.os.Bundle
import com.example.cosmin.recyclerviewapp.R
import com.example.cosmin.recyclerviewapp.util.BaseActivity

class ListActivity : BaseActivity() {

    companion object {
        private const val LIST_FRAG = "LIST_FRAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        var fragment = supportFragmentManager.findFragmentByTag(LIST_FRAG)
        if (fragment == null) {
            fragment = ListFragment.newInstance()
        }

        addFragmentToActivity(
            supportFragmentManager,
            fragment,
            R.id.root_activity_list,
            LIST_FRAG
        )
    }
}