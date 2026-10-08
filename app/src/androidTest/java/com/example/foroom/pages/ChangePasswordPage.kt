package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.design_system.R as DesignR
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ChangePasswordPage {
    val password: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.passwordInput)))
    val repeatPassword: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.repeatPasswordInput)))
    val confirmButton: Matcher<View> = withId(DesignR.id.actionButton)
}
