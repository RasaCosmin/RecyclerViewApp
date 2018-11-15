package com.example.cosmin.recyclerviewapp.list


import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.cosmin.recyclerviewapp.R
import com.example.cosmin.recyclerviewapp.RoomDemoApplication
import com.example.cosmin.recyclerviewapp.create.CreateActivity
import com.example.cosmin.recyclerviewapp.data.ListItem
import com.example.cosmin.recyclerviewapp.detail.DetailActivity
import com.example.cosmin.recyclerviewapp.viewModel.ListItemCollectionViewModel
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class ListFragment : Fragment() {

    private var listOfData: MutableList<ListItem>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var toolbar: Toolbar

    lateinit var listItemCollectionViewModel: ListItemCollectionViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as RoomDemoApplication)
            .applicationComponent?.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListItemCollectionViewModel::class.java)

        listItemCollectionViewModel.getListItems().observe(this, Observer<MutableList<ListItem>> {
            if (it == null) {
                setListData(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.rec_list_activity)
        toolbar = view.findViewById(R.id.tlb_list_activity)
        toolbar.setTitle(R.string.title_toolbar)
        toolbar.setLogo(R.drawable.ic_view_list_white_24dp)
        toolbar.titleMarginStart = 72

        val fab = view.findViewById<FloatingActionButton>(R.id.fab_create_new_item)
        fab.setOnClickListener { startCreateActivity() }
        return view
    }

    private fun startCreateActivity() {
        startActivity(Intent(activity, CreateActivity::class.java))
    }

    private fun setListData(listOfData: MutableList<ListItem>?) {
        this.listOfData = listOfData
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = CustomAdapter()
        recyclerView.adapter = adapter
        val itemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        itemDecoration.setDrawable(ContextCompat.getDrawable(activity!!, R.drawable.divider_white)!!)
        recyclerView.addItemDecoration(itemDecoration)
        val itemTouchHelper = ItemTouchHelper(createHelperCallback())
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun startDetailActivity(itemId: String, viewRoot: View) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(EXTRA_ITEM_ID, itemId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.window?.enterTransition = Fade(Fade.IN)
            activity?.window?.enterTransition = Fade(Fade.OUT)
            val options = ActivityOptions
                .makeSceneTransitionAnimation(
                    activity,
                    android.util.Pair<View, String>(
                        viewRoot.findViewById(R.id.imv_list_item_circle),
                        getString(R.string.transition_drawable)
                    ),
                    android.util.Pair<View, String>(
                        viewRoot.findViewById(R.id.lbl_message),
                        getString(R.string.transition_message)
                    ),
                    android.util.Pair<View, String>(
                        viewRoot.findViewById(R.id.lbl_date_and_time),
                        getString(R.string.transition_time_and_date)
                    )
                )
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    companion object {
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ListFragment.
         */
        @JvmStatic
        fun newInstance() =
            ListFragment()
    }

    private fun createHelperCallback(): ItemTouchHelper.Callback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerVire: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition

                listItemCollectionViewModel.deleteListItem(listOfData?.get(position)!!)

                listOfData?.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }
    }


    private inner class CustomAdapter : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val v = layoutInflater.inflate(R.layout.item_data, parent, false)
            return CustomViewHolder(v)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val listItem = listOfData?.get(position)!!
            holder.coloredCircle.setImageResource(listItem.colorResource)
            holder.message.text = listItem.message
            holder.dateAndTime.text = listItem.itemId
            holder.loading.visibility = View.INVISIBLE
        }

        //Helps the Adapter decide how many items it will need to manage
        override fun getItemCount(): Int = listOfData?.size ?: 0

        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            val coloredCircle: CircleImageView = itemView.findViewById(R.id.imv_list_item_circle)
            val dateAndTime: TextView = itemView.findViewById(R.id.lbl_date_and_time)
            val message: TextView = itemView.findViewById(R.id.lbl_message)
            val loading: ProgressBar = itemView.findViewById(R.id.pro_item_data)
            private val container: ViewGroup = itemView.findViewById(R.id.root_list_item)

            init {
                container.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                val listItem = listOfData?.get(this.adapterPosition)
                if (listItem != null)
                    startDetailActivity(listItem.itemId, v!!)
            }
        }
    }
}
