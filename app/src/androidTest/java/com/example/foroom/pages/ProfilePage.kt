package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible

class ProfilePage {

    fun verifyProfileDisplayed() {
        onView(withId(R.id.changePasswordItem))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))

        onView(withId(R.id.changeLanguageItem))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))

        onView(withId(R.id.signOutItem))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }

    fun verifyHomeDisplayed() {
        onView(withId(R.id.homeNavigationProfile))
            .check(matches(isDisplayed()))
    }

    fun openChangePassword() {
        onView(withId(R.id.changePasswordItem))
            .waitUntilVisible(10)
            .perform(click())
    }

    fun openChangeLanguage() {
        onView(withId(R.id.changeLanguageItem))
            .waitUntilVisible(10)
            .perform(click())
    }

    fun verifyGeorgianProfileLabel() {
        onView(withText("ენის შეცვლა"))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }

    fun verifyEnglishProfileLabel() {
        onView(withText("Change Language"))
            .waitUntilVisible(10)
            .check(matches(isDisplayed()))
    }

    fun signOut() {
        onView(withId(R.id.signOutItem))
            .waitUntilVisible(10)
            .perform(click())
    }
}