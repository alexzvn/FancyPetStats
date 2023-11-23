package dev.alexzvn.petstats.utils

import dev.alexzvn.petstats.FancyPetStats
import org.bukkit.event.Listener


fun String.debug() {
    FancyPetStats.instance().logger.info(this)
}

fun String.warning() {
    FancyPetStats.instance().logger.warning(this)
}

fun String.error() {
    FancyPetStats.instance().logger.severe(this)
}

fun Listener.listen() {
    FancyPetStats.instance().server.pluginManager.registerEvents(this, FancyPetStats.instance())
}

fun nextTick(callback: () -> Unit) {
    FancyPetStats.instance().server.scheduler.runTask(FancyPetStats.instance(), callback)
}

fun delay(delay: Long, callback: () -> Unit) {
    FancyPetStats.instance().server.scheduler.runTaskLater(FancyPetStats.instance(), callback, delay)
}