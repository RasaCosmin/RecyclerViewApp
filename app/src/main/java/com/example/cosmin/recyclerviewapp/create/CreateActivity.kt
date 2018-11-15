package com.example.cosmin.recyclerviewapp.create

import android.os.Bundle
import com.example.cosmin.recyclerviewapp.R
import com.example.cosmin.recyclerviewapp.util.BaseActivity

class CreateActivity : BaseActivity() {
    companion object {
        private const val CREATE_FRAG = "CREATE_FRAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        var fragment = supportFragmentManager.findFragmentByTag(CREATE_FRAG)
        if (fragment == null) {
            fragment = CreateFragment.newInstance()
        }

        addFragmentToActivity(
            supportFragmentManager,
            fragment,
            R.id.root_activity_create,
            CREATE_FRAG
        )
    }
}
