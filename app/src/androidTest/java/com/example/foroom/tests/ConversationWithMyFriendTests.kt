package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.data.Constants
import com.example.foroom.data.Constants.ACADEMY_QUESTION_PREFIX
import com.example.foroom.data.Constants.DRINK_MESSAGE_PREFIX
import com.example.foroom.data.Constants.GREETING_PREFIX
import com.example.foroom.data.Constants.HISTORY_PREFIX
import com.example.foroom.data.Constants.REPLY_PREFIX
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.ConversationSteps
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
class ConversationWithMyFriendTests : KoinComponent {
    private val store: ForoomUserDataStore by inject()
    private val login = LoginSteps()
    private val profile = ProfileSteps()
    private val conversation = ConversationSteps()

    private val resetSession = object : ExternalResource() {
        override fun before() = runBlocking { store.clearUserData() }
    }
    private val activity = ActivityScenarioRule(ForoomActivity::class.java)
    @get:Rule val rules: RuleChain = RuleChain.outerRule(resetSession).around(activity)

    private fun signIn(user: String) {
        login.verifyLoginScreen()
        login.enterUsername(user)
        login.enterPassword(Constants.PASSWORD)
        login.clickLogin()
        profile.verifyHomeDisplayed()
    }

    private fun signOut() {
        profile.openProfile()
        profile.signOut()
        login.verifyLoginScreen()
    }

    @Test fun sendMessageInJohnWeekAndVerifyPersistence() {
        val message = "$DRINK_MESSAGE_PREFIX${System.currentTimeMillis()}"
        signIn(Constants.USER_A)
        conversation.searchChat(Constants.JOHN_WEEK_CHAT)
        conversation.openChat(Constants.JOHN_WEEK_CHAT)
        conversation.verifyChatTitle(Constants.JOHN_WEEK_CHAT)
        conversation.enterMessage(message)
        conversation.tapSend()
        conversation.verifyMessage(message)
        conversation.closeChat()
        conversation.searchChat(Constants.JOHN_WEEK_CHAT)
        conversation.openChat(Constants.JOHN_WEEK_CHAT)
        conversation.verifyChatTitle(Constants.JOHN_WEEK_CHAT)
        conversation.verifyMessage(message)
    }

    @Test fun sendAutomationAcademyQuestionInPersonalChat() {
        val question = "$ACADEMY_QUESTION_PREFIX${System.currentTimeMillis()}"
        signIn(Constants.USER_A)
        conversation.searchChat(Constants.PERSONAL_CHAT)
        conversation.openChat(Constants.PERSONAL_CHAT)
        conversation.verifyChatTitle(Constants.PERSONAL_CHAT)
        conversation.enterMessage(question)
        conversation.tapSend()
        conversation.verifyMessage(question)
    }

    @Test fun continueConversationUsingAnotherAccount() {
        val suffix = System.currentTimeMillis().toString()
        val greeting = "$GREETING_PREFIX$suffix"
        val reply = "$REPLY_PREFIX$suffix"
        signIn(Constants.USER_A)
        conversation.searchChat(Constants.SHARED_CHAT)
        conversation.openChat(Constants.SHARED_CHAT)
        conversation.verifyChatTitle(Constants.SHARED_CHAT)
        conversation.enterMessage(greeting)
        conversation.tapSend()
        conversation.verifyMessage(greeting)
        repeat(Constants.HISTORY_MESSAGE_COUNT) {
            val history = "$HISTORY_PREFIX$suffix #$it"
            conversation.enterMessage(history)
            conversation.tapSend()
            conversation.verifyMessage(history)
        }
        conversation.closeChat()
        signOut()

        signIn(Constants.USER_B)
        conversation.searchChat(Constants.SHARED_CHAT)
        conversation.openChat(Constants.SHARED_CHAT)
        conversation.verifyChatTitle(Constants.SHARED_CHAT)
        conversation.findOlderMessage(greeting, Constants.USER_A)
        conversation.enterMessage(reply)
        conversation.tapSend()
        conversation.verifyMessage(reply)
        conversation.findOlderMessage(
            greeting,
            Constants.USER_A
        )
        conversation.verifyMessage(
            reply,
            Constants.USER_B
        )
        conversation.closeChat()
        signOut()

        signIn(Constants.USER_A)
        conversation.searchChat(Constants.SHARED_CHAT)
        conversation.openChat(Constants.SHARED_CHAT)
        conversation.verifyChatTitle(Constants.SHARED_CHAT)
        conversation.verifyMessage(reply, Constants.USER_B)
    }
}
