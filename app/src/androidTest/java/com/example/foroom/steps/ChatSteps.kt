package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage

class ChatSteps {

    private val createChatPage = CreateChatPage()
    private val chatsPage = ChatsPage()

    fun openCreateChat() {
        onView(withId(R.id.homeNavigationCreateChat))
            .waitUntilVisible(10)
            .perform(click())
    }

    fun enterChatName(chatName: String) {
        createChatPage.enterChatName(chatName)
    }

    fun selectChatImage() {
        createChatPage.chooseChatImage()
    }

    fun createChat() {
        createChatPage.createChat()
    }

    fun verifyCreatedChatOpened(chatName: String) {
        chatsPage.verifyCreatedChatOpened(chatName)
    }

    fun closeChat() {
        chatsPage.closeChat()
    }

    fun searchChat(chatName: String) {
        chatsPage.searchChat(chatName)
    }

    fun verifyChatInList(chatName: String) {
        chatsPage.verifyChatInList(chatName)
    }
}