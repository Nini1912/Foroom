package com.example.foroom.pages

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matchers.allOf

class ConversationPage {
    private val input = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.messageInput)))

    fun verifyTitle(title: String) {
        onView(allOf(withId(DesignR.id.chatNameTextView), withText(title), isDisplayed()))
            .waitUntilVisible(15).check(matches(isDisplayed()))
    }

    fun send(text: String) {
        onView(input).waitUntilVisible(10).perform(replaceText(text), closeSoftKeyboard())
        onView(withId(R.id.sendMessageButton)).perform(click())
        awaitMessage(text)
    }

    fun close() {
        onView(allOf(withId(R.id.closeButton), isDisplayed())).perform(click())
    }

    private fun messageRow(view: View, text: String): Boolean {
        if (view.findViewById<TextView>(DesignR.id.messageTextView)?.text?.toString() == text) return true
        return false
    }

    fun visibleMessage(text: String, sender: String? = null): Boolean {
        var found = false
        onView(withId(R.id.messagesRecyclerView)).check { view, _ ->
            val rv = view as RecyclerView
            for (i in 0 until rv.childCount) {
                val row = rv.getChildAt(i)
                if (messageRow(row, text) &&
                    (sender == null || row.findViewById<TextView>(DesignR.id.userNameTextView)?.text?.toString() == sender)) {
                    found = true
                    break
                }
            }
        }
        return found
    }

    fun awaitMessage(text: String, sender: String? = null, seconds: Long = 15) {
        val deadline = System.currentTimeMillis() + seconds * 1000
        while (System.currentTimeMillis() < deadline) {
            if (visibleMessage(text, sender)) return
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().waitForIdleSync()
            Thread.sleep(100)
        }
        throw AssertionError("Message '$text' from '${sender ?: "any sender"}' not visible")
    }

    fun messageListBounds(): IntArray {
        var bounds = intArrayOf(0, 0, 0)
        onView(withId(R.id.messagesRecyclerView)).check { view, _ ->
            val pos = IntArray(2)
            view.getLocationOnScreen(pos)
            bounds = intArrayOf(pos[0] + view.width / 2, pos[1] + view.height / 3, pos[1] + view.height * 2 / 3)
        }
        return bounds
    }

    fun itemCount(): Int {
        var count = 0
        onView(withId(R.id.messagesRecyclerView)).check { view, _ ->
            count = (view as RecyclerView).adapter?.itemCount ?: 0
        }
        return count
    }

    fun scrollToPosition(position: Int) {
        onView(withId(R.id.messagesRecyclerView)).perform(object : androidx.test.espresso.ViewAction {
            override fun getConstraints() = isAssignableFrom(RecyclerView::class.java)
            override fun getDescription() = "scroll messages to adapter position $position"
            override fun perform(controller: androidx.test.espresso.UiController, view: View) {
                (view as RecyclerView).scrollToPosition(position)
                controller.loopMainThreadUntilIdle()
            }
        })
    }
}
