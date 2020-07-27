package com.islamversity.reyan

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.facebook.soloader.SoLoader
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun setup() {
            SoLoader.setInTestMode()
        }
    }

    @Test
    fun appLaunchesCorrectly() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(
            ViewMatchers.withId(
                R.id.root
            )
        ).check(
            matches(
                isDisplayed()
            )
        )
    }
}