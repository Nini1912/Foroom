package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.ProfilePage

class ProfileSteps {

    private val profilePage = ProfilePage()
    private val changePasswordPage = ChangePasswordPage()
    private val changeLanguagePage = ChangeLanguagePage()

    fun openProfile() {
        onView(withId(R.id.homeNavigationProfile))
            .waitUntilVisible(10)
            .perform(click())
    }

    fun verifyProfileDisplayed() {
        profilePage.verifyProfileDisplayed()
    }

    fun openChangePassword() {
        profilePage.openChangePassword()
    }

    fun changePassword(newPassword: String) {
        changePasswordPage.enterPassword(newPassword)
        changePasswordPage.repeatPassword(newPassword)
        changePasswordPage.confirmPasswordChange()
    }

    fun openChangeLanguage() {
        profilePage.openChangeLanguage()
    }

    fun selectGeorgian() {
        changeLanguagePage.selectGeorgian()
    }

    fun selectEnglish() {
        changeLanguagePage.selectEnglish()
    }

    fun verifyGeorgianLanguage() {
        profilePage.verifyGeorgianProfileLabel()
    }

    fun verifyEnglishLanguage() {
        profilePage.verifyEnglishProfileLabel()
    }

    fun signOut() {
        profilePage.signOut()
    }
}