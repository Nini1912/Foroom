package com.example.foroom.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible

class ChangeLanguagePage {

    fun selectGeorgian() {
        onView(withId(R.id.languageButtonGeo))
            .waitUntilVisible(10)
            .perform(click())
    }

    fun selectEnglish() {
        onView(withId(R.id.languageButtonEng))
            .waitUntilVisible(10)
            .perform(click())
    }
}