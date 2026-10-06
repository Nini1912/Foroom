package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ChangePasswordPage {

    private val passwordEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.passwordInput))
    )

    private val repeatPasswordEditText: Matcher<View> = allOf(
        withId(DesignR.id.inputEditText),
        isDescendantOfA(withId(R.id.repeatPasswordInput))
    )

    fun enterPassword(password: String) {
        onView(passwordEditText)
            .waitUntilVisible(10)
            .perform(
                replaceText(password),
                closeSoftKeyboard()
            )
    }

    fun repeatPassword(password: String) {
        onView(repeatPasswordEditText)
            .waitUntilVisible(10)
            .perform(
                replaceText(password),
                closeSoftKeyboard()
            )
    }

    fun confirmPasswordChange() {
        onView(withId(DesignR.id.actionButton))
            .waitUntilVisible(10)
            .perform(click())
    }
}