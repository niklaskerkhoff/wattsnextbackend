package de.niklaskerkhoff.wattsnextbackend.model

import kotlin.test.Test
import kotlin.test.assertEquals

class ModelTest {
    @Test
    fun `it should return the correct points`() {
        val points = Model().getPoints()
        assertEquals(5, points)
    }
}