package com.example.wiprointerview

import com.example.wiprointerview.model.Item
import com.example.wiprointerview.model.ItemResult
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.example.wiprointerview.network.Result

@RunWith(JUnit4::class)
class MainViewModelTest {

    @Test
    fun testItem() {
        val item = Item("beavers", "beavers are second only", "images.png")
        assertEquals("beavers", item.title)
        assertEquals("beavers are second only", item.description)
        assertEquals("images.png", item.imageHref)

        val item1 = Item("", "", "")
        assertTrue(item1.title == "")
        assertFalse(item1.description != "")
        assertEquals("", item1.imageHref)
    }

    @Test
    fun testItemResult() {
        val title = "beavers"
        val list = ArrayList<Item>()
        val itemResult = ItemResult(title, list)
        assertEquals("beavers", itemResult.title)
        assertEquals(0, itemResult.rows.size)
        val item = Item("beavers", "beavers are second only", "images.png")
        list.add(item)
        val itemResult1 = ItemResult(title, list)
        assertEquals("beavers", itemResult1.title)
        assertTrue(itemResult.rows.size == 1)


    }

    @Test
    fun testResult() {
        val title = "beavers"
        val list: ArrayList<Item> = ArrayList()
        val item = Item("beavers", "beavers are second only", "images.png")
        list.add(item)
        val itemResult = ItemResult(title, list)
        val success = Result.success(itemResult)
        assertTrue(success.status == Result.Status.SUCCESS)
        assertTrue(success.data?.rows?.size == 1)

        val error = Result.error("data is not available",itemResult)
        assertTrue(error.status == Result.Status.ERROR)
        assertTrue(error.message.equals("data is not available"))

        val loading = Result.loading(null)
        assertTrue(loading.status == Result.Status.LOADING)
        assertTrue(loading.data ==null)

    }


}
