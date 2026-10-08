package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.Helper.waitUntilVisible
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

    companion object {
        private const val USER_A = "user1"
        private const val USER_B = "user2"
        private const val PASSWORD = "user123"
        private const val JOHN_WEEK = "johnWeek"
        private const val PERSONAL_CHAT = "Nino Beridze"
        private const val SHARED_CHAT = "something"
    }

    private fun signIn(user: String) {
        login.verifyLoginScreen()
        login.login(user, PASSWORD)
        androidx.test.espresso.Espresso.onView(
            androidx.test.espresso.matcher.ViewMatchers.withId(com.alternator.foroom.R.id.homeNavigationProfile)
        ).let { it.waitUntilVisible(15) }
    }

    private fun signOut() {
        profile.openProfile()
        profile.signOut()
        login.verifyLoginScreen()
    }

    @Test fun sendMessageInJohnWeekAndVerifyPersistence() {
        val message = "let's go for a drink ${System.currentTimeMillis()}"
        signIn(USER_A)
        conversation.openChat(JOHN_WEEK)
        conversation.sendAndVerify(message)
        conversation.closeChat()
        conversation.openChat(JOHN_WEEK)
        conversation.verifyMessage(message)
    }

    @Test fun sendAutomationAcademyQuestionInPersonalChat() {
        val question = "Which module do you like most in the Automation Academy? ${System.currentTimeMillis()}"
        signIn(USER_A)
        conversation.openChat(PERSONAL_CHAT)
        conversation.sendAndVerify(question)
    }

    @Test fun continueConversationUsingAnotherAccount() {
        val suffix = System.currentTimeMillis().toString()
        val greeting = "Hello from User A $suffix"
        val reply = "Hello from User B $suffix"
        signIn(USER_A)
        conversation.openChat(SHARED_CHAT)
        conversation.sendAndVerify(greeting)
        repeat(26) { conversation.sendAndVerify("History $suffix #$it") }
        conversation.closeChat()
        signOut()

        signIn(USER_B)
        conversation.openChat(SHARED_CHAT)
        conversation.findOlderMessage(greeting, USER_A)
        conversation.sendAndVerify(reply)
        conversation.closeChat()
        signOut()

        signIn(USER_A)
        conversation.openChat(SHARED_CHAT)
        conversation.verifyMessage(reply, USER_B)
    }
}
