package com.remziakgoz.todolistwithcompose.presentation.views

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.remziakgoz.todolistwithcompose.domain.model.Item
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddItemScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testButtonAddsItemToSaveFunction() {
        val savedItems = mutableListOf<Item>()

        val saveFunction: (Item) -> Unit = {
            savedItems.add(it)
        }

        composeTestRule.setContent {
            AddItemScreen(saveFunction = saveFunction)
        }

        val toDoName = "Test ToDo"
        composeTestRule.onNodeWithText("Enter ToDo").performTextInput(toDoName)

        composeTestRule.onNodeWithText("Add").performClick()

        assertEquals(1, savedItems.size)
        assertEquals(toDoName, savedItems[0].toDoName)
    }

    @Test
    fun testEmptyInputDoesNotCallSaveFunction() {
        val savedItems = mutableListOf<Item>()
        val saveFunction: (Item) -> Unit = {
            savedItems.add(it)
        }

        composeTestRule.setContent {
            AddItemScreen(saveFunction = saveFunction)
        }

        composeTestRule.onNodeWithText("Add").performClick()

        assertEquals(0, savedItems.size)
    }
}