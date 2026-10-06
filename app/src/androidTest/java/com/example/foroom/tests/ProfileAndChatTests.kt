package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests : KoinComponent {

    private val userDataStore: ForoomUserDataStore by inject()

    private val loginSteps = LoginSteps()
    private val profileSteps = ProfileSteps()
    private val chatSteps = ChatSteps()

    private val clearUserDataRule = object : ExternalResource() {

        override fun before() {
            runBlocking {
                userDataStore.clearUserData()
            }
        }
    }

    private val activityRule =
        ActivityScenarioRule(ForoomActivity::class.java)

    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(clearUserDataRule)
        .around(activityRule)

    companion object {
        private const val USERNAME = "user"

        // Make sure this is the password that currently works
        // for your account on THIS emulator.
        private const val CURRENT_PASSWORD = "user123"

        private const val NEW_PASSWORD = "User1234!"

        // Replace with your actual full name.
        private const val FULL_NAME = "Nino"
    }

    @Test
    fun changePasswordAndVerifyLogin() {

        loginSteps.login(
            USERNAME,
            CURRENT_PASSWORD
        )

        loginSteps.verifyHomeDisplayed()

        profileSteps.openProfile()
        profileSteps.verifyProfileDisplayed()

        profileSteps.openChangePassword()

        profileSteps.changePassword(
            NEW_PASSWORD
        )

        loginSteps.verifyLoginScreenDisplayed()

        loginSteps.login(
            USERNAME,
            NEW_PASSWORD
        )

        loginSteps.verifyHomeDisplayed()

        // Restore original password so the test account
        // can be reused by the other independent scenarios.
        profileSteps.openProfile()
        profileSteps.verifyProfileDisplayed()

        profileSteps.openChangePassword()

        profileSteps.changePassword(
            CURRENT_PASSWORD
        )

        loginSteps.verifyLoginScreenDisplayed()
    }

    @Test
    fun changeLanguageGeorgianToEnglishAndBack() {

        loginSteps.login(
            USERNAME,
            CURRENT_PASSWORD
        )

        loginSteps.verifyHomeDisplayed()

        profileSteps.openProfile()
        profileSteps.verifyProfileDisplayed()

        // Establish Georgian as the starting state.
        profileSteps.openChangeLanguage()
        profileSteps.selectGeorgian()

        profileSteps.verifyGeorgianLanguage()

        // Georgian -> English
        profileSteps.openChangeLanguage()
        profileSteps.selectEnglish()

        profileSteps.verifyEnglishLanguage()

        // English -> Georgian
        profileSteps.openChangeLanguage()
        profileSteps.selectGeorgian()

        profileSteps.verifyGeorgianLanguage()
    }

    @Test
    fun createChatAndFindItInChatList() {

        val chatName =
            "$FULL_NAME ${System.currentTimeMillis()}"

        loginSteps.login(
            USERNAME,
            CURRENT_PASSWORD
        )

        loginSteps.verifyHomeDisplayed()

        chatSteps.openCreateChat()

        chatSteps.enterChatName(chatName)

        chatSteps.selectChatImage()

        chatSteps.createChat()

        chatSteps.verifyCreatedChatOpened(
            chatName
        )

        chatSteps.closeChat()

        chatSteps.searchChat(
            chatName
        )

        chatSteps.verifyChatInList(
            chatName
        )
    }
}