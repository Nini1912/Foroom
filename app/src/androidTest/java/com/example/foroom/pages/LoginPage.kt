package com.example.foroom.pages

import android.view.View
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
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class LoginPage {

    private val usernameEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    private val passwordEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val usernameError: Matcher<View> = allOf(
        withId(DesignR.id.descriptionTextView),
        isDescendantOfA(withId(R.id.userNameInput))
    )

    private val passwordError: Matcher<View> = allOf(
        withId(DesignR.id.descriptionTextView),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    fun verifyLoginScreenDisplayed() {
        onView(withId(R.id.logInButton))
            .check(matches(isDisplayed()))

        onView(withId(R.id.signUpButton))
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

    fun tapLogIn() {
        onView(withId(R.id.logInButton))
            .perform(click())
    }

    fun tapSignUp() {
        onView(withId(R.id.signUpButton))
            .perform(click())
    }

    fun verifyUsernameErrorDisplayed() {
        onView(usernameError)
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }

    fun verifyPasswordErrorDisplayed() {
        onView(passwordError)
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }
}