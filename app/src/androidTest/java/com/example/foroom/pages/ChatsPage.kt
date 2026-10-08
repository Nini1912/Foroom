package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ChatsPage {

    private val searchChatEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.searchChatInput))
    )

    // Create-chat and chat screens both have a closeButton, so only match the visible one
    private val closeButton: Matcher<View> = allOf(
        withId(R.id.closeButton),
        isDisplayed()
    )

    fun closeChat() {
        onView(closeButton)
            .waitUntilVisible(10)
            .perform(click())
    }

    fun searchChat(chatName: String) {
        onView(searchChatEditText)
            .waitUntilVisible(10)
            .perform(
                replaceText(chatName),
                closeSoftKeyboard()
            )
    }

    // The chat header's title (chatNameTextView) belongs to design_system
    fun verifyCreatedChatOpened(chatName: String) {
        onView(
            allOf(
                withId(DesignR.id.chatNameTextView),
                withText(chatName),
                isDisplayed()
            )
        )
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))
    }

    // Chat card title (chatTitleTextView) inside the chats RecyclerView
    fun verifyChatInList(chatName: String) {
        onView(
            allOf(
                withId(DesignR.id.chatTitleTextView),
                withText(chatName),
                isDescendantOfA(withId(R.id.chatsRecyclerView))
            )
        )
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))
    }
}