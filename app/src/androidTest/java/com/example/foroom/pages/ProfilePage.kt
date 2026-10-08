package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alternator.foroom.R
import com.example.foroom.data.Constants
import org.hamcrest.Matcher

class ProfilePage {
    val homeNavigation: Matcher<View> = withId(R.id.homeNavigationProfile)
    val changePassword: Matcher<View> = withId(R.id.changePasswordItem)
    val changeLanguage: Matcher<View> = withId(R.id.changeLanguageItem)
    val signOut: Matcher<View> = withId(R.id.signOutItem)
    val georgianLabel: Matcher<View> = withText(Constants.GEORGIAN_LANGUAGE_LABEL)
    val englishLabel: Matcher<View> = withText(Constants.ENGLISH_LANGUAGE_LABEL)
}
