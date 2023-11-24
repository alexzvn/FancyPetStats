package dev.alexzvn.petstats.utils

import dev.alexzvn.petstats.FancyPetStats
import net.Indyuce.mmocore.api.player.PlayerData
import net.luckperms.api.LuckPerms
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.util.UUID

private fun plugin (): JavaPlugin {
    return FancyPetStats.instance()
}

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

fun nextTick(callback: () -> Unit): BukkitTask {
    return FancyPetStats.instance().server.scheduler.runTask(FancyPetStats.instance(), callback)
}

fun interval(tick: Long, callback: () -> Unit): BukkitTask {
    return FancyPetStats.instance().server.scheduler.runTaskTimer(FancyPetStats.instance(), callback, tick, tick)
}

fun delay(delay: Long, callback: () -> Unit) {
    FancyPetStats.instance().server.scheduler.runTaskLater(FancyPetStats.instance(), callback, delay)
}

fun withPlugin(name: String, callback: (plugin: Plugin) -> Unit) {
    plugin().server.pluginManager.getPlugin(name)?.apply(callback)
}

fun Player.mmoData() = PlayerData.get(this).mmoPlayerData
fun UUID.mmoData() = PlayerData.get(this).mmoPlayerData