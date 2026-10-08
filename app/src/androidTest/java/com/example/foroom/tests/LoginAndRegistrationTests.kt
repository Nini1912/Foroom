package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
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

    companion object {
        private const val EXISTING_USERNAME = "user"
        private const val INVALID_PASSWORD = "WrongPassword123"
        private const val VALID_REGISTRATION_PASSWORD = "Espresso123"
    }

    @Test
    fun validUsernameAndInvalidPassword() {
        loginSteps.verifyLoginScreen()

        loginSteps.login(
            username = EXISTING_USERNAME,
            password = INVALID_PASSWORD
        )

        loginSteps.verifyPasswordError()
    }

    @Test
    fun invalidUsernameAndInvalidPassword() {
        val invalidUsername =
            "nonexistent_${System.currentTimeMillis()}"

        loginSteps.verifyLoginScreen()

        loginSteps.login(
            username = invalidUsername,
            password = INVALID_PASSWORD
        )

        loginSteps.verifyUsernameError()
        loginSteps.verifyPasswordError()
    }

    @Test
    fun successfulRegistration() {
        val uniqueUsername =
            "espresso_${System.currentTimeMillis()}"

        loginSteps.verifyLoginScreen()
        loginSteps.navigateToRegistration()

        registrationSteps.verifyRegistrationScreen()

        registrationSteps.fillRegistrationForm(
            username = uniqueUsername,
            password = VALID_REGISTRATION_PASSWORD
        )

        registrationSteps.selectAvatar()
        registrationSteps.submitRegistration()

        registrationSteps.verifySuccessfulRegistration()
    }
}
