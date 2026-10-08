package com.example.foroom.pages

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher

class RegistrationPage {

    private val usernameEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    private val passwordEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val repeatPasswordEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.repeatPasswordInput))
    )

    fun verifyRegistrationScreenDisplayed() {
        onView(withId(R.id.repeatPasswordInput))
            .check(matches(isDisplayed()))

        onView(withId(R.id.listView))
            .check(matches(isDisplayed()))
    }

    fun enterUsername(username: String) {
        onView(usernameEditText)
            .perform(
                replaceText(username),
                closeSoftKeyboard()
            )
    }

    fun enterPassword(password: String) {
        onView(passwordEditText)
            .perform(
                replaceText(password),
                closeSoftKeyboard()
            )
    }

    fun enterRepeatPassword(password: String) {
        onView(repeatPasswordEditText)
            .perform(
                replaceText(password),
                closeSoftKeyboard()
            )
    }

    fun selectAvatar() {
        onView(
            childAtPosition(
                withId(R.id.listView),
                1
            )
        )
            .waitUntilVisible(15)
            .perform(click())
    }

    fun tapSignUp() {
        onView(withId(R.id.signUpButton))
            .perform(click())
    }

    fun verifyHomeScreenDisplayed() {
        onView(withId(R.id.navBar))
            .waitUntilVisible(15)
            .check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>,
        position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {

            override fun describeTo(description: Description) {
                description.appendText(
                    "child at position $position in parent "
                )
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent

                return parent is ViewGroup &&
                        parentMatcher.matches(parent) &&
                        parent.getChildAt(position) == view
            }
        }
    }
}