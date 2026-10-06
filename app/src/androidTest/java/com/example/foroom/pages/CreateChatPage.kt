package com.example.foroom.pages

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.waitUntilVisible
import com.example.shared.model.Image
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import java.util.concurrent.TimeoutException

class CreateChatPage {

    private val chatNameEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.chatNameInput))
    )

    fun enterChatName(chatName: String) {
        onView(chatNameEditText)
            .waitUntilVisible(10)
            .perform(
                replaceText(chatName),
                closeSoftKeyboard()
            )
    }

    fun chooseChatImage(index: Int = 1) {
        waitUntilEmojisLoaded()

        onView(chatImageAt(index))
            .perform(click())
    }

    fun createChat() {
        onView(withId(R.id.createChatButton))
            .perform(click())
    }

    private fun waitUntilEmojisLoaded(timeoutMs: Long = 15_000) {
        val deadline = System.currentTimeMillis() + timeoutMs

        while (System.currentTimeMillis() < deadline) {
            var loaded = false

            onView(withId(R.id.chatImageChooser)).check { view, noViewFoundException ->
                if (view == null) throw noViewFoundException
                val images = (view as ImageChooserListView).images
                loaded = images.isNotEmpty() && images.none { it.id == Image.BLANK_IMAGE_ID }
            }

            if (loaded) return
            Thread.sleep(100)
        }

        throw TimeoutException("Chat emojis were not loaded within $timeoutMs ms")
    }

    // Matches the Nth ImageChooserItemView inside the chooser (counting row by row)
    private fun chatImageAt(index: Int): Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("chat image item at position $index in chatImageChooser")
        }

        override fun matchesSafely(item: View): Boolean {
            if (item !is ImageChooserItemView) return false

            var parent = item.parent
            while (parent != null && parent !is ImageChooserListView) {
                parent = parent.parent
            }
            val chooser = parent as? ImageChooserListView ?: return false

            val items = mutableListOf<View>()
            for (r in 0 until chooser.childCount) {
                val row = chooser.getChildAt(r) as? ViewGroup ?: continue
                for (c in 0 until row.childCount) {
                    val child = row.getChildAt(c)
                    if (child is ImageChooserItemView) items.add(child)
                }
            }

            return items.getOrNull(index) === item
        }
    }
}