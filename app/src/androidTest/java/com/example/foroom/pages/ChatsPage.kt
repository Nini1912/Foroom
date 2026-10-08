package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.design_system.components.chat.ForoomChatCardView
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ChatsPage {
    val searchInput: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.searchChatInput)))
    val closeButton: Matcher<View> = allOf(withId(R.id.closeButton), isDisplayed())
    fun openedTitle(title: String): Matcher<View> = allOf(withId(DesignR.id.chatNameTextView), withText(title), isDisplayed())
    fun listTitle(title: String): Matcher<View> = allOf(withId(DesignR.id.chatTitleTextView), withText(title), isDescendantOfA(withId(R.id.chatsRecyclerView)))
    fun cardOpenButton(title: String): Matcher<View> {
        val card = allOf(isAssignableFrom(ForoomChatCardView::class.java), hasDescendant(allOf(withId(DesignR.id.chatTitleTextView), withText(title))))
        return allOf(withId(DesignR.id.sendMessageButton), isDescendantOfA(card), isDisplayed())
    }
}
