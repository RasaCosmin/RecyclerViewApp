package com.example.cosmin.recyclerviewapp

import com.example.cosmin.recyclerviewapp.data.DataSourceInterface
import com.example.cosmin.recyclerviewapp.data.ListItem
import com.example.cosmin.recyclerviewapp.logic.Controller
import com.example.cosmin.recyclerviewapp.view.ViewInterface
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.ArrayList

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
/**
 * Test Double:
 * Specifically a "Mock"
 */
@RunWith(MockitoJUnitRunner::class)
class ControllerUnitTest {
    @Mock
    lateinit var dataSource: DataSourceInterface

    @Mock
    lateinit var view: ViewInterface

    lateinit var controller: Controller

    companion object {
        val TEST_ITEM: ListItem = ListItem(
            "6:30AM 06/01/2017",
            "Check out content like Fragmented Podcast to expose yourself to the knowledge, ideas",
            R.color.RED
        )

        val POSITION = 3
    }

    @Before
    fun setUpTest() {
        MockitoAnnotations.initMocks(this)
        controller = Controller(view, dataSource)
    }

    @Test
    fun onGetListDataSuccessful() {
        //Set up any data we need for the test
        val listOfData = ArrayList<ListItem>()
        listOfData.add(TEST_ITEM)

        //Set up out Mocks to return the data we want
        Mockito.`when`(dataSource.getListOfData())
            .thenReturn(listOfData)

        //Call the method(Unit) we are testing
        controller.getListFromDataSource()

        //Check how the Tested Class responds to the data it receives
        //or test it's behaviour
        Mockito.verify(view).setUpAdapterAndView(listOfData)
    }

     @Test
     fun onGetListDataUnsuccessful() {
    /**************************
     *
     * Unit Test Homework:
     *
     * Implement the "View", so that when we don't recieve a List, it shows some kind of
     * error message to the user. This is common practice that you should learn!
     *
     * I've written some hints you'll have to decipher into Java code:
     *************************/
         var listOfData: ArrayList<ListItem>? = null
    //1 Set up your Mock dataSource
    Mockito.`when`(dataSource.getListOfData())
        .thenReturn(listOfData)

    //2 Call the method you wish to test on the Controller
    controller.getListFromDataSource()

    //3 Verify that the View has been told to show a message (I'd suggest showing a Toast for now)
    Mockito.verify(view).setUpAdapterAndView(listOfData)

    //Profit???

     }

    @Test
    fun onListItemClicked(){
        controller.onListItemClick(TEST_ITEM)

        Mockito.verify(view).startDetailActivity(
            TEST_ITEM.dateAndTime,
            TEST_ITEM.message,
            TEST_ITEM.colorResource)
    }

    @Test
    fun onCreateNewListItemClick(){
        Mockito.`when`(dataSource.createNewListItem())
            .thenReturn(TEST_ITEM)

        controller.createNewListItem()

        Mockito.verify(view).addNewListItemToView(TEST_ITEM)
    }

    @Test
    fun onSwipeToDelete(){
        controller.onListItemSwiped(POSITION, TEST_ITEM)

        Mockito.verify(dataSource).deleteListItem(TEST_ITEM)
        Mockito.verify(view).deleteListItemAt(POSITION)

        Mockito.verify(view).showUndoSnackbar()
    }

    @Test
    fun onUndoDeleteOperation(){
        controller.onListItemSwiped(POSITION, TEST_ITEM)

        controller.onUndoConfirmed()

        Mockito.verify(dataSource).insertListItem(TEST_ITEM)

        Mockito.verify(view).insertListItemAt(POSITION, TEST_ITEM)
    }

    @Test
    fun onSnackbarTimeout(){
        controller.onListItemSwiped(POSITION, TEST_ITEM)

        controller.onSnackBarTimeout()
    }
}
