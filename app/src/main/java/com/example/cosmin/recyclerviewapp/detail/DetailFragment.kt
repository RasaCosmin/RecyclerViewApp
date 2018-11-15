package com.example.cosmin.recyclerviewapp.detail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cosmin.recyclerviewapp.R
import com.example.cosmin.recyclerviewapp.RoomDemoApplication
import com.example.cosmin.recyclerviewapp.data.ListItem
import com.example.cosmin.recyclerviewapp.viewModel.ListItemViewModel
import javax.inject.Inject

class DetailFragment : Fragment() {

    private lateinit var dateAndTime: TextView
    private lateinit var message: TextView
    private lateinit var coloredBackground: ImageView

    private lateinit var itemId: String

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var listItemViewModel: ListItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as RoomDemoApplication)
            .applicationComponent?.inject(this)

        this.itemId = arguments?.getString(EXTRA_ITEM_ID)!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listItemViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(listItemViewModel::class.java)

        listItemViewModel.getListItemById(this.itemId).observe(this,
            Observer<ListItem> { listItem ->
                if(listItem != null){
                    dateAndTime.text = listItem.itemId
                    message.text = listItem.message
                    coloredBackground.setImageResource(listItem.colorResource)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_detail, container, false)
        dateAndTime = view.findViewById(R.id.lbl_date_and_time_header)
        message = view.findViewById(R.id.lbl_message_body)
        coloredBackground = view.findViewById(R.id.imv_colored_background)
        return  view
    }


    companion object {
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DetailFragment.
         */

        @JvmStatic
        fun newInstance(itemId: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(EXTRA_ITEM_ID, itemId)
            fragment.arguments = args
            return fragment
        }
    }
}
