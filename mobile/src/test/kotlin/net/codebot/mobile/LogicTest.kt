package net.codebot.mobile

import net.codebot.mobile.model.Logic
import kotlin.test.Test
import kotlin.test.assertEquals


/*
 * Most of our functionalities are not testable through a unittest because most functions directly call
 * API functions from Spotify, Supabase, or Firebase. And the logic is mostly done through SQL queries
 * via Supabase. The function below was the only non-UI function that could be called in isolation.
 */
class LogicTest {
    @Test
    fun winningProbabilityTest() {
        assertEquals(0.24f, Logic.winningProbability(1500, 1300), absoluteTolerance = 0.001f)
        assertEquals(0.849f, Logic.winningProbability(1200, 1500), absoluteTolerance = 0.0001f)
        assertEquals(0.5f, Logic.winningProbability(1000, 1000))
    }
}