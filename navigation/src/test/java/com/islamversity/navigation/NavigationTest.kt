package com.islamversity.navigation

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NavigationTest {

    @Test
    fun `navigation returns correct object`(){
        assertThat(ControllerFactory.createController(Screens.Test))
            .isInstanceOf(TestController::class.java)
    }
}