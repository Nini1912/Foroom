package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class LoginPage {
    val username: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.userNameInput)))
    val password: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.passwordInput)))
    val usernameError: Matcher<View> = allOf(withId(DesignR.id.descriptionTextView), isDescendantOfA(withId(R.id.userNameInput)))
    val passwordError: Matcher<View> = allOf(withId(DesignR.id.descriptionTextView), isDescendantOfA(withId(R.id.passwordInput)))
    val loginButton: Matcher<View> = withId(R.id.logInButton)
    val signUpButton: Matcher<View> = withId(R.id.signUpButton)
}
