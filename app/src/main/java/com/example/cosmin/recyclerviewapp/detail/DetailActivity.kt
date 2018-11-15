package com.example.cosmin.recyclerviewapp.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cosmin.recyclerviewapp.R
import com.example.cosmin.recyclerviewapp.util.BaseActivity

class DetailActivity : BaseActivity() {

    companion object {
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"
        private const val DETAIL_FRAG = "DETAIL_FRAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        if(intent.hasExtra(EXTRA_ITEM_ID)) {

            val itemId = intent.getStringExtra(EXTRA_ITEM_ID)

            var fragment = supportFragmentManager.findFragmentByTag(DETAIL_FRAG)
            if (fragment == null) {
                fragment = DetailFragment.newInstance(itemId)
            }

            addFragmentToActivity(
                supportFragmentManager,
                fragment,
                R.id.root_activity_detail,
                DETAIL_FRAG
            )
        }else{
            Toast.makeText(this, R.string.error_no_extra_found, Toast.LENGTH_LONG).show();
        }
    }
}
