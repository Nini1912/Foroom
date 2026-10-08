package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class CreateChatPage {
    val createChatNavigation: Matcher<View> = withId(R.id.homeNavigationCreateChat)
    val chatName: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.chatNameInput)))
    val imageChooser: Matcher<View> = withId(R.id.chatImageChooser)
    val createButton: Matcher<View> = withId(R.id.createChatButton)
}
