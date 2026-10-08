package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ConversationPage {
    val messageInput: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.messageInput)))
    val sendButton: Matcher<View> = withId(R.id.sendMessageButton)
    val closeButton: Matcher<View> = allOf(withId(R.id.closeButton), isDisplayed())
    val messageList: Matcher<View> = withId(R.id.messagesRecyclerView)
    val messageTextId: Int = DesignR.id.messageTextView
    val senderNameId: Int = DesignR.id.userNameTextView
    fun title(title: String): Matcher<View> = allOf(withId(DesignR.id.chatNameTextView), withText(title), isDisplayed())
}
