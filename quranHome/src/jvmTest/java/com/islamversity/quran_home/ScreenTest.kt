package com.islamversity.home

import androidx.test.filters.MediumTest
import com.islamversity.home.view.HomeView
import com.islamversity.navigation.ControllerFactory
import com.islamversity.navigation.Screens
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScreenTest {

    @Test
    @MediumTest
    fun homeScreenBoundedCorrectly(){
        assertThat(ControllerFactory.createController(Screens.Home))
            .isInstanceOf(HomeView::class.java)
    }

}