package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.data.Constants
import com.example.foroom.pages.LoginPage

class LoginSteps {
    private val page = LoginPage()
    fun verifyLoginScreen() {
        onView(page.loginButton).check(matches(isDisplayed()))
        onView(page.signUpButton).check(matches(isDisplayed()))
    }
    fun verifyLoginScreenDisplayed() = verifyLoginScreen()
    fun enterUsername(username: String) {
        onView(page.username).perform(replaceText(username), closeSoftKeyboard())
    }
    fun enterPassword(password: String) {
        onView(page.password).perform(replaceText(password), closeSoftKeyboard())
    }
    fun clickLogin() { onView(page.loginButton).perform(click()) }
    fun verifyPasswordError() {
        onView(page.passwordError).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).check(matches(isDisplayed()))
    }
    fun verifyUsernameError() {
        onView(page.usernameError).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).check(matches(isDisplayed()))
    }
    fun navigateToRegistration() { onView(page.signUpButton).perform(click()) }
}
