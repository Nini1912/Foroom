package com.example.foroom.steps

import com.example.foroom.pages.LoginPage

class LoginSteps {

    private val loginPage = LoginPage()

    fun verifyLoginScreen() {
        loginPage.verifyLoginScreenDisplayed()
    }

    fun login(username: String, password: String) {
        loginPage.enterUsername(username)
        loginPage.enterPassword(password)
        loginPage.tapLogIn()
    }

    fun verifyPasswordError() {
        loginPage.verifyPasswordErrorDisplayed()
    }

    fun verifyUsernameError() {
        loginPage.verifyUsernameErrorDisplayed()
    }

    fun navigateToRegistration() {
        loginPage.tapSignUp()
    }
}