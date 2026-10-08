package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.data.Constants
import com.example.foroom.data.Constants.CHAT_NAME_PREFIX
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

    @Test
    fun changePasswordAndVerifyLogin() {

        loginSteps.enterUsername(Constants.EXISTING_USERNAME)
        loginSteps.enterPassword(Constants.PASSWORD)
        loginSteps.clickLogin()

        profileSteps.verifyHomeDisplayed()

        profileSteps.openProfile()
        profileSteps.verifyProfileDisplayed()

        profileSteps.openChangePassword()

        profileSteps.enterNewPassword(Constants.NEW_PASSWORD)
        profileSteps.repeatNewPassword(Constants.NEW_PASSWORD)
        profileSteps.confirmPasswordChange()

        loginSteps.verifyLoginScreenDisplayed()

        loginSteps.enterUsername(Constants.EXISTING_USERNAME)
        loginSteps.enterPassword(Constants.NEW_PASSWORD)
        loginSteps.clickLogin()

        profileSteps.verifyHomeDisplayed()

        profileSteps.openProfile()
        profileSteps.verifyProfileDisplayed()

        profileSteps.openChangePassword()

        profileSteps.enterNewPassword(Constants.PASSWORD)
        profileSteps.repeatNewPassword(Constants.PASSWORD)
        profileSteps.confirmPasswordChange()

        loginSteps.verifyLoginScreenDisplayed()
    }

    @Test
    fun changeLanguageGeorgianToEnglishAndBack() {

        loginSteps.enterUsername(Constants.EXISTING_USERNAME)
        loginSteps.enterPassword(Constants.PASSWORD)
        loginSteps.clickLogin()

        profileSteps.verifyHomeDisplayed()

        profileSteps.openProfile()
        profileSteps.verifyProfileDisplayed()

        profileSteps.openChangeLanguage()
        profileSteps.selectGeorgian()

        profileSteps.verifyGeorgianLanguage()

        profileSteps.openChangeLanguage()
        profileSteps.selectEnglish()

        profileSteps.verifyEnglishLanguage()

        profileSteps.openChangeLanguage()
        profileSteps.selectGeorgian()

        profileSteps.verifyGeorgianLanguage()
    }

    @Test
    fun createChatAndFindItInChatList() {

        val chatName =
            "${CHAT_NAME_PREFIX} ${System.currentTimeMillis()}"

        loginSteps.enterUsername(Constants.EXISTING_USERNAME)
        loginSteps.enterPassword(Constants.PASSWORD)
        loginSteps.clickLogin()

        profileSteps.verifyHomeDisplayed()

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