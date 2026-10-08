package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.ChatImageActions
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.data.Constants
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage

class ChatSteps {
    private val create = CreateChatPage()
    private val chats = ChatsPage()
    fun openCreateChat() {
        onView(create.createChatNavigation).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).perform(click())
    }
    fun enterChatName(name: String) {
        onView(create.chatName).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).perform(replaceText(name), closeSoftKeyboard())
    }
    fun selectChatImage() {
        onView(create.imageChooser).waitUntilVisible(Constants.NAVIGATION_WAIT_SECONDS.toLong())
            .perform(ChatImageActions.waitForRealImages())
            .perform(ChatImageActions.selectImageAt(Constants.AVATAR_INDEX))
    }
    fun createChat() {
        onView(create.createButton).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).perform(click())
    }
    fun verifyCreatedChatOpened(name: String) {
        onView(chats.openedTitle(name)).waitUntilVisible(Constants.NAVIGATION_WAIT_SECONDS.toLong()).check(matches(isDisplayed()))
    }
    fun closeChat() {
        onView(chats.closeButton).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).perform(click())
    }
    fun searchChat(name: String) {
        onView(chats.searchInput).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).perform(replaceText(name), closeSoftKeyboard())
    }
    fun verifyChatInList(name: String) {
        onView(chats.listTitle(name)).waitUntilVisible(Constants.NAVIGATION_WAIT_SECONDS.toLong()).check(matches(isDisplayed()))
    }
}
