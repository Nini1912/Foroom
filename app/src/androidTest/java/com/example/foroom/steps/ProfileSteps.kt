package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.data.Constants
import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage

class ProfileSteps {
    private val profile = ProfilePage()
    private val password = ChangePasswordPage()
    private val language = ChangeLanguagePage()
    private fun assertVisible(matcher: org.hamcrest.Matcher<android.view.View>) {
        onView(matcher).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).check(matches(isDisplayed()))
    }
    private fun tap(matcher: org.hamcrest.Matcher<android.view.View>) {
        onView(matcher).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).perform(click())
    }
    fun openProfile() = tap(profile.homeNavigation)
    fun verifyProfileDisplayed() {
        assertVisible(profile.changePassword)
        assertVisible(profile.changeLanguage)
        assertVisible(profile.signOut)
    }
    fun openChangePassword() = tap(profile.changePassword)
    fun enterNewPassword(value: String) {
        onView(password.password).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).perform(replaceText(value), closeSoftKeyboard())
    }
    fun repeatNewPassword(value: String) {
        onView(password.repeatPassword).waitUntilVisible(Constants.DEFAULT_WAIT_SECONDS.toLong()).perform(replaceText(value), closeSoftKeyboard())
    }
    fun confirmPasswordChange() = tap(password.confirmButton)
    fun openChangeLanguage() = tap(profile.changeLanguage)
    fun selectGeorgian() = tap(language.georgian)
    fun selectEnglish() = tap(language.english)
    fun verifyGeorgianLanguage() = assertVisible(profile.georgianLabel)
    fun verifyEnglishLanguage() = assertVisible(profile.englishLabel)
    fun signOut() = tap(profile.signOut)
    fun verifyHomeDisplayed() = assertVisible(profile.homeNavigation)
}
