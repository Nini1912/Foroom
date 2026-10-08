package com.example.foroom.steps

import android.view.View
import android.widget.TextView
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.data.Constants
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.ConversationPage
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.foroom.Helper.swipeUpSlowly

class ConversationSteps {
    private val chats = ChatsPage()
    private val page = ConversationPage()

    fun searchChat(title: String) {
        onView(chats.searchInput).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong())
            .perform(replaceText(title), closeSoftKeyboard())
    }

    fun openChat(title: String) {
        onView(chats.cardOpenButton(title))
            .waitUntilVisible(Constants.NAVIGATION_WAIT_SECONDS.toLong())
            .perform(clickPartiallyVisibleButton())
    }

    fun verifyChatTitle(title: String) {
        onView(page.title(title)).waitUntilVisible(Constants.NAVIGATION_WAIT_SECONDS.toLong())
            .check(matches(isDisplayed()))
    }

    fun enterMessage(message: String) {
        onView(page.messageInput).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong())
            .perform(replaceText(message), closeSoftKeyboard())
    }

    fun tapSend() { onView(page.sendButton).perform(click()) }

    fun verifyMessage(message: String, sender: String? = null) = awaitMessage(message, sender)

    fun closeChat() { onView(page.closeButton).perform(click()) }

    private var lastSenderMismatch: String? = null

    private fun visibleMessage(
        text: String,
        sender: String? = null
    ): Boolean {
        var found = false

        onView(page.messageList).check { view, error ->
            if (error != null) throw error

            val recyclerView = view as RecyclerView

            for (i in 0 until recyclerView.childCount) {
                val row = recyclerView.getChildAt(i)

                val actualText = row
                    .findViewById<TextView>(page.messageTextId)
                    ?.text
                    ?.toString()
                    ?.trim()

                if (actualText != text.trim()) continue

                val actualSender = row
                    .findViewById<TextView>(page.senderNameId)
                    ?.text
                    ?.toString()
                    ?.trim()

                Log.d(
                    "ConversationTest",
                    "Message='$actualText', " +
                            "Expected sender='$sender', " +
                            "Actual sender='$actualSender'"
                )

                if (sender == null || actualSender == sender.trim()) {
                    found = true
                    lastSenderMismatch = null
                    break
                }

                lastSenderMismatch =
                    "Message '$text' found, but expected sender " +
                            "'$sender' and actual sender was '$actualSender'"
            }
        }

        return found
    }
    private fun awaitMessage(
        text: String,
        sender: String? = null
    ) {
        val deadline =
            System.currentTimeMillis() +
                    Constants.NAVIGATION_WAIT_SECONDS * 1000L

        lastSenderMismatch = null

        while (System.currentTimeMillis() < deadline) {
            if (visibleMessage(text, sender)) {
                return
            }

            InstrumentationRegistry
                .getInstrumentation()
                .waitForIdleSync()

            Thread.sleep(Constants.POLL_INTERVAL_MS)
        }

        throw AssertionError(
            "Message '$text' from '${sender ?: "any sender"}' " +
                    "was not verified. " +
                    (lastSenderMismatch ?: "Message not visible.")
        )
    }

    private fun itemCount(): Int {
        var count = 0
        onView(page.messageList).check { view, _ -> count = (view as RecyclerView).adapter?.itemCount ?: 0 }
        return count
    }

    private fun scrollToPosition(position: Int) {
        onView(page.messageList).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> = isAssignableFrom(RecyclerView::class.java)
            override fun getDescription() = "scroll messages to adapter position $position"
            override fun perform(controller: UiController, view: View) {
                (view as RecyclerView).scrollToPosition(position)
                controller.loopMainThreadUntilIdle()
            }
        })
    }

    fun findOlderMessage(
        message: String,
        sender: String
    ) {
        val deadline =
            System.currentTimeMillis() +
                    Constants.HISTORY_SEARCH_TIMEOUT_MS

        var attempts = 0
        lastSenderMismatch = null

        while (
            attempts < Constants.MAX_HISTORY_SWIPE_ATTEMPTS &&
            System.currentTimeMillis() < deadline
        ) {
            if (visibleMessage(message, sender)) {
                return
            }

            onView(page.messageList).perform(swipeUpSlowly())

            attempts++

            InstrumentationRegistry
                .getInstrumentation()
                .waitForIdleSync()
        }

        if (visibleMessage(message, sender)) {
            return
        }

        throw AssertionError(
            "Could not find message '$message' " +
                    "from sender '$sender' after $attempts swipes. " +
                    (lastSenderMismatch ?: "Message was not found.")
        )
    }
    private fun clickPartiallyVisibleButton(): ViewAction = object : ViewAction {
        override fun getConstraints(): Matcher<View> = allOf(isDisplayed(), isEnabled(), isClickable())
        override fun getDescription() = "Click a partially visible button"
        override fun perform(uiController: UiController, view: View) {
            if (!view.performClick()) throw AssertionError("Button click was not handled")
            uiController.loopMainThreadUntilIdle()
        }
    }
}
