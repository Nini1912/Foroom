package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.data.Constants
import com.example.foroom.data.Constants.REGISTRATION_USERNAME_PREFIX
import com.example.foroom.data.Constants.NONEXISTENT_USERNAME_PREFIX
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@RunWith(AndroidJUnit4::class)
class LoginAndRegistrationTests : KoinComponent {

    private val userDataStore: ForoomUserDataStore by inject()

    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()

    private val clearUserDataRule = object : ExternalResource() {
        override fun before() {
            runBlocking {
                userDataStore.clearUserData()
            }
        }
    }

    private val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(clearUserDataRule)
        .around(activityRule)

    @Test
    fun validUsernameAndInvalidPassword() {
        loginSteps.verifyLoginScreen()

        loginSteps.enterUsername(Constants.EXISTING_USERNAME)
        loginSteps.enterPassword(Constants.INVALID_PASSWORD)
        loginSteps.clickLogin()

        loginSteps.verifyPasswordError()
    }

    @Test
    fun invalidUsernameAndInvalidPassword() {
        val invalidUsername =
            "$NONEXISTENT_USERNAME_PREFIX${System.currentTimeMillis()}"

        loginSteps.verifyLoginScreen()

        loginSteps.enterUsername(invalidUsername)
        loginSteps.enterPassword(Constants.INVALID_PASSWORD)
        loginSteps.clickLogin()

        loginSteps.verifyUsernameError()
        loginSteps.verifyPasswordError()
    }

    @Test
    fun successfulRegistration() {
        val uniqueUsername =
            "$REGISTRATION_USERNAME_PREFIX${System.currentTimeMillis()}"

        loginSteps.verifyLoginScreen()
        loginSteps.navigateToRegistration()

        registrationSteps.verifyRegistrationScreen()

        registrationSteps.enterUsername(uniqueUsername)
        registrationSteps.enterPassword(Constants.REGISTRATION_PASSWORD)
        registrationSteps.enterRepeatPassword(Constants.REGISTRATION_PASSWORD)

        registrationSteps.selectAvatar()
        registrationSteps.submitRegistration()

        registrationSteps.verifySuccessfulRegistration()
    }
}
