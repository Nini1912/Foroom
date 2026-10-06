package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.util.HumanReadables
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.waitUntilVisible
import com.example.shared.model.Image
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
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
        onView(withId(R.id.chatImageChooser))
            .waitUntilVisible(15)
            .perform(waitForRealImages())
            .perform(selectImageAt(index))
    }

    fun createChat() {
        onView(withId(R.id.createChatButton))
            .waitUntilVisible(10)
            .perform(click())
    }

    private fun waitForRealImages(
        timeoutMs: Long = 30_000
    ): ViewAction {

        return object : ViewAction {

            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(ImageChooserListView::class.java)
            }

            override fun getDescription(): String {
                return "wait until real chat emojis are loaded"
            }

            override fun perform(
                uiController: UiController,
                view: View
            ) {
                val chooser = view as ImageChooserListView
                val endTime = System.currentTimeMillis() + timeoutMs

                do {
                    uiController.loopMainThreadUntilIdle()

                    val images = chooser.images

                    val loaded = images.isNotEmpty() &&
                            images.any { image ->
                                image.id != Image.BLANK_IMAGE_ID
                            }

                    if (loaded) {
                        return
                    }

                    uiController.loopMainThreadForAtLeast(100)

                } while (System.currentTimeMillis() < endTime)

                throw PerformException.Builder()
                    .withActionDescription(description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(
                        TimeoutException(
                            "Real chat emojis were not loaded within $timeoutMs ms"
                        )
                    )
                    .build()
            }
        }
    }

    private fun selectImageAt(index: Int): ViewAction {

        return object : ViewAction {

            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(ImageChooserListView::class.java)
            }

            override fun getDescription(): String {
                return "select chat image at index $index"
            }

            override fun perform(
                uiController: UiController,
                view: View
            ) {
                val chooser = view as ImageChooserListView

                if (chooser.images.isEmpty()) {
                    throw AssertionError(
                        "Chat image list is empty"
                    )
                }

                if (index !in chooser.images.indices) {
                    throw AssertionError(
                        "Chat image index $index does not exist. " +
                                "Available images: ${chooser.images.size}"
                    )
                }

                if (chooser.images[index].id == Image.BLANK_IMAGE_ID) {
                    throw AssertionError(
                        "Chat image at index $index is still a loading placeholder"
                    )
                }

                chooser.selectImageAt(index)

                uiController.loopMainThreadUntilIdle()
            }
        }
    }
}