package dev.alexzvn.petstats.listeners

import dev.alexzvn.petstats.modifier.ModifierHolder
import dev.alexzvn.petstats.utils.interval
import dev.alexzvn.petstats.utils.mmoData
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent


class PermissionChangeListener : Listener {
    init {
        interval(20 * 3) {
            Bukkit.getServer().onlinePlayers.forEach { check(it) }
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        check(event.player)
    }

    @EventHandler
    fun playerLeave(event: PlayerQuitEvent) {
        check(event.player)
    }

    private fun check(player: Player) {
        ModifierHolder.permissions.modifiers.forEach { (perm, stat) ->
            if (player.hasPermission(perm)) {
                stat.bind(player.mmoData())
            }

            if (! player.hasPermission(perm)) {
                stat.unbind(player.mmoData())
            }
        }
    }
}