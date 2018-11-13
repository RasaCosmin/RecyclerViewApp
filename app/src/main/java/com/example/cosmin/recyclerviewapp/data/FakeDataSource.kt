package com.example.cosmin.recyclerviewapp.data

import com.example.cosmin.recyclerviewapp.R
import java.util.*

/**
 * Test Double:
 * Specifically a "Fake"
 */

class FakeDataSource : DataSourceInterface {
    companion object {
        private const val SIZE_OF_COLLECTION: Int = 12
        val random = Random()
    }

    private val datesAndTimes =
        arrayOf("6:30AM 06/01/2017", "9:26PM 04/22/2013", "2:01PM 12/02/2015", "2:43AM 09/7/2018")

    private val messages = arrayOf(
        "Check out content like Fragmented Podcast to expose yourself to the knowledge, ideas, " + "and opinions of experts in your field",
        "Look at Open Source Projects like Android Architecture Blueprints to see how experts" + " design and build Apps",
        "Write lots of Code and Example Apps. Writing good Quality Code in an efficient manner " + "is a Skill to be practiced like any other.",
        "If at first something doesn't make any sense, find another explanation. We all " + "learn/teach different from each other. Find an explanation that speaks to you."
    )

    private val drawables = intArrayOf(
        R.drawable.green_drawable,
        R.drawable.red_drawable,
        R.drawable.blue_drawable,
        R.drawable.yellow_drawable
    )

    override fun getListOfData(): MutableList<ListItem> {
        val listOfData = ArrayList<ListItem>()

        for (i in 0..SIZE_OF_COLLECTION) {
            listOfData.add(createNewListItem())
        }


        return listOfData
    }

    override fun createNewListItem(): ListItem{
        val randOne = random.nextInt(4)
        val randTwo = random.nextInt(4)
        val randThree = random.nextInt(4)

        return ListItem(datesAndTimes[randOne], messages[randTwo], drawables[randThree])
    }

    override fun deleteListItem(listItem: ListItem) {

    }

    override fun insertListItem(tempListItem: ListItem) {

    }
}