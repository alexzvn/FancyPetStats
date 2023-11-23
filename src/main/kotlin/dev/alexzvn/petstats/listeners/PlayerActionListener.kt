package dev.alexzvn.petstats.listeners

import dev.alexzvn.petstats.modifier.PetModifierHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerActionListener: Listener {
    @EventHandler
    fun clearStats(event: PlayerQuitEvent) {
        PetModifierHolder.forget(event.player.uniqueId)
    }
}