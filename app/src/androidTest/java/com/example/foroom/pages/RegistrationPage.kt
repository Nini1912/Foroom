package com.example.foroom.pages

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.foroom.data.Constants
import com.example.design_system.R as DesignR
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher

class RegistrationPage {
    val username: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.userNameInput)))
    val password: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.passwordInput)))
    val repeatPassword: Matcher<View> = allOf(withId(DesignR.id.inputEditText), isDescendantOfA(withId(R.id.repeatPasswordInput)))
    val repeatPasswordContainer: Matcher<View> = withId(R.id.repeatPasswordInput)
    val avatarList: Matcher<View> = withId(R.id.listView)
    val avatar: Matcher<View> = childAtPosition(avatarList, Constants.AVATAR_INDEX)
    val signUpButton: Matcher<View> = withId(R.id.signUpButton)
    val homeNavigation: Matcher<View> = withId(R.id.navBar)

    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("child at position $position in parent ")
                parentMatcher.describeTo(description)
            }
            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && parent.getChildAt(position) == view
            }
        }
}
