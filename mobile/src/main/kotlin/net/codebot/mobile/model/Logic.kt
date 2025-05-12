package net.codebot.mobile.model

import kotlin.math.pow

object Logic {

    fun winningProbability(rank1: Int, rank2: Int): Float {
        return 1.0f / (1 + 10.0f.pow((rank1 - rank2) / 400.0f))
    }

    // Add other utility functions here
}