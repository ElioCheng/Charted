package net.codebot.mobile.controller

import net.codebot.mobile.view.RecommendationEvent
import net.codebot.mobile.model.Model
import net.codebot.mobile.view.ViewEvent
import shared.entities.MusicItem
import shared.entities.Task

class Controller(val model: Model) {
    fun invoke(event: ViewEvent, obj: Any) {
        when(event) {
            ViewEvent.Exit -> return
        }
    }

}