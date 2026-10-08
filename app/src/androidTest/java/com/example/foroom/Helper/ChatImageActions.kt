package com.example.foroom.Helper

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.util.HumanReadables
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.data.Constants
import com.example.shared.model.Image
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

object ChatImageActions {
    fun waitForRealImages(
        timeoutMs: Long = Constants.IMAGE_LOAD_TIMEOUT_MS
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

                    uiController.loopMainThreadForAtLeast(Constants.POLL_INTERVAL_MS)

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

    fun selectImageAt(index: Int): ViewAction {

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
