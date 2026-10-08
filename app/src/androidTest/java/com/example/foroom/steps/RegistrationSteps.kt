package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.data.Constants
import com.example.foroom.pages.RegistrationPage

class RegistrationSteps {
    private val page = RegistrationPage()
    fun verifyRegistrationScreen() {
        onView(page.repeatPasswordContainer).check(matches(isDisplayed()))
        onView(page.avatarList).check(matches(isDisplayed()))
    }
    fun enterUsername(username: String) { onView(page.username).perform(replaceText(username), closeSoftKeyboard()) }
    fun enterPassword(password: String) { onView(page.password).perform(replaceText(password), closeSoftKeyboard()) }
    fun enterRepeatPassword(password: String) { onView(page.repeatPassword).perform(replaceText(password), closeSoftKeyboard()) }
    fun selectAvatar() { onView(page.avatar).waitUntilVisible(Constants.NAVIGATION_WAIT_SECONDS.toLong()).perform(click()) }
    fun submitRegistration() { onView(page.signUpButton).perform(click()) }
    fun verifySuccessfulRegistration() {
        onView(page.homeNavigation).waitUntilVisible(Constants.NAVIGATION_WAIT_SECONDS.toLong()).check(matches(isDisplayed()))
    }
}
