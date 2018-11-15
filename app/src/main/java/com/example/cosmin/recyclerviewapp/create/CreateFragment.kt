package com.example.cosmin.recyclerviewapp.create


import android.app.ListActivity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.cosmin.recyclerviewapp.R
import com.example.cosmin.recyclerviewapp.RoomDemoApplication
import com.example.cosmin.recyclerviewapp.data.ListItem
import com.example.cosmin.recyclerviewapp.viewModel.NewListItemViewModel
import com.viewpagerindicator.CirclePageIndicator
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateFragment : Fragment() {
    private lateinit var drawablePager: ViewPager
    private lateinit var pageIndicator: CirclePageIndicator
    private lateinit var back: ImageButton
    private lateinit var done: ImageButton
    private lateinit var messageInput: EditText

    private lateinit var pagerAdapter: PagerAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var newListItemViewModel: NewListItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        (activity?.application as RoomDemoApplication)
            .applicationComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newListItemViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewListItemViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        back = view.findViewById(R.id.imb_create_back)
        back.setOnClickListener { startListActivity() }

        done = view.findViewById(R.id.imb_create_done)
        back.setOnClickListener {
            val item =
                ListItem(getDate(), messageInput.text.toString(), getDrawableResource(drawablePager.currentItem));
            newListItemViewModel.addNewItemToDatabase(item)
            startListActivity()
        }

        messageInput = view.findViewById(R.id.edt_create_message)

        drawablePager = view.findViewById(R.id.vp_create_drawable)

        pagerAdapter = DrawablePagerAdapter()
        drawablePager.adapter = pagerAdapter

        pageIndicator = view.findViewById(R.id.vpi_create_drawable)
        pageIndicator.setViewPager(drawablePager)

        return view
    }

    private fun startListActivity() {
        startActivity(Intent(activity, ListActivity::class.java))
    }

    private fun getDrawableResource(pagerIndicator: Int): Int {
        return when (pagerIndicator) {
            0 -> R.drawable.red_drawable
            1 -> R.drawable.blue_drawable
            2 -> R.drawable.green_drawable
            3 -> R.drawable.yellow_drawable
            else -> 0
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CreateFragment.
         */
        @JvmStatic
        fun newInstance() =
            CreateFragment()
    }

    inner class DrawablePagerAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(activity)
            val pagerItem = inflater.inflate(R.layout.item_drawable_pager, container, false) as ImageView

            when (position) {
                0 -> pagerItem.setImageResource(R.drawable.red_drawable)
                1 -> pagerItem.setImageResource(R.drawable.blue_drawable)
                2 -> pagerItem.setImageResource(R.drawable.green_drawable)
                3 -> pagerItem.setImageResource(R.drawable.yellow_drawable)
            }

            container.addView(pagerItem)
            return pagerItem
        }

        override fun getCount(): Int = 4

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }

    private fun getDate(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy/MM/dd/kk:mm:ss")
        return dateFormat.format(currentDate)
    }
}
