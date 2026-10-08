package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.swiper
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.ConversationPage
import org.hamcrest.Matchers.allOf

class ConversationSteps {
    private val chats = ChatsPage()
    private val conversation = ConversationPage()

    fun openChat(title: String) {
        chats.searchChat(title)
        onView(allOf(withId(DesignR.id.chatTitleTextView), withText(title), isDisplayed()))
            .waitUntilVisible(15).perform(click())
        conversation.verifyTitle(title)
    }

    fun sendAndVerify(message: String) = conversation.send(message)
    fun verifyMessage(message: String, sender: String? = null) = conversation.awaitMessage(message, sender)
    fun closeChat() = conversation.close()

    fun findOlderMessage(message: String, sender: String) {
        // Existing helper uses a fixed X=500; only call when this coordinate is inside the list.
        val b = conversation.messageListBounds()
        if (b[0] >= 500) {
            swiper(b[2], b[1], 350)
            if (conversation.visibleMessage(message, sender)) return
        }
        // RecyclerView positions are device-independent and bounded, unlike pixel swipes.
        val count = conversation.itemCount()
        for (position in 0 until minOf(count, 300)) {
            conversation.scrollToPosition(position)
            if (conversation.visibleMessage(message, sender)) return
        }
        throw AssertionError("Could not find older message '$message' from '$sender' in $count rows")
    }
}
