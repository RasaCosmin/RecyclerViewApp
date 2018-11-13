package com.example.cosmin.recyclerviewapp.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.transition.Fade
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.cosmin.recyclerviewapp.R
import com.example.cosmin.recyclerviewapp.data.FakeDataSource
import com.example.cosmin.recyclerviewapp.data.ListItem
import com.example.cosmin.recyclerviewapp.logic.Controller
import de.hdodenhof.circleimageview.CircleImageView

class ListActivity : AppCompatActivity(), ViewInterface, View.OnClickListener {
    companion object {
        private const val EXTRA_DATE_TIME = "EXTRA_DATE_TIME"
        private const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        private const val EXTRA_DRAWABLE = "EXTRA_DRAWABLE"
    }

    private var listOfData: MutableList<ListItem>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var toolbar: Toolbar

    private lateinit var controller: Controller


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rec_list_activity)
        toolbar = findViewById(R.id.tlb_list_activity)
        toolbar.setTitle(R.string.title_toolbar)
        toolbar.setLogo(R.drawable.ic_view_list_white_24dp)
        toolbar.titleMarginStart = 72

        val fab = findViewById<FloatingActionButton>(R.id.fab_create_new_item)
        fab.setOnClickListener(this)

        //This is dependency injection
        controller = Controller(this, FakeDataSource())

    }

    override fun startDetailActivity(dateAndTime: String, message: String, colorResource: Int, viewRoot: View) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_DATE_TIME, dateAndTime)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_DRAWABLE, colorResource)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = Fade(Fade.IN)
            window.enterTransition = Fade(Fade.OUT)

            val options = ActivityOptions
                .makeSceneTransitionAnimation(
                    this,
                    android.util.Pair<View, String>(viewRoot.findViewById(R.id.imv_list_item_circle), getString(R.string.transition_drawable)),
                    android.util.Pair<View, String>(viewRoot.findViewById(R.id.lbl_message), getString(R.string.transition_message)),
                    android.util.Pair<View, String>(viewRoot.findViewById(R.id.lbl_date_and_time), getString(R.string.transition_date))
                )
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    override fun setUpAdapterAndView(listOfData: MutableList<ListItem>?) {
        this.listOfData = listOfData

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = CustomAdapter()
        recyclerView.adapter = adapter

        val itemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_white)!!)

        recyclerView.addItemDecoration(itemDecoration)

        val itemTouchHelper = ItemTouchHelper(createHelperCallback())
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun addNewListItemToView(listItem: ListItem) {
        listOfData?.add(listItem)

        val endOfList = (listOfData?.size ?: 1) - 1

        adapter.notifyItemInserted(endOfList)
        recyclerView.smoothScrollToPosition(endOfList)
    }

    override fun deleteListItemAt(position: Int) {
        listOfData?.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun showUndoSnackbar() {
        Snackbar.make(
            findViewById(R.id.root_list_activity),
            getString(R.string.action_delete_item),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.action_undo) { controller.onUndoConfirmed() }
            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)

                    controller.onSnackBarTimeout()
                }
            }).show()
    }

    override fun insertListItemAt(position: Int, listItem: ListItem) {
        listOfData?.add(position, listItem)
        adapter.notifyItemInserted(position)
    }

    override fun onClick(v: View?) {
        val viewId = v?.id ?: -1

        if (viewId == R.id.fab_create_new_item) {
            //user wish to create a new item
            controller.createNewListItem()
        }
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

                controller.onListItemSwiped(position, listOfData?.get(position))
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
            holder.dateAndTime.text = listItem.dateAndTime

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
                    controller.onListItemClick(listItem, v!!)
            }

        }
    }
}
