package com.example.foroom.steps

import com.example.foroom.pages.RegistrationPage

class RegistrationSteps {

    private val registrationPage = RegistrationPage()

    fun verifyRegistrationScreen() {
        registrationPage.verifyRegistrationScreenDisplayed()
    }

    fun fillRegistrationForm(
        username: String,
        password: String
    ) {
        registrationPage.enterUsername(username)
        registrationPage.enterPassword(password)
        registrationPage.enterRepeatPassword(password)
    }

    fun selectAvatar() {
        registrationPage.selectAvatar()
    }

    fun submitRegistration() {
        registrationPage.tapSignUp()
    }

    fun verifySuccessfulRegistration() {
        registrationPage.verifyHomeScreenDisplayed()
    }
}