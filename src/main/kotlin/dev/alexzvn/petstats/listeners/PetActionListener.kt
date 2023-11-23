package dev.alexzvn.petstats.listeners

import dev.alexzvn.petstats.modifier.PetModifierHolder
import dev.alexzvn.petstats.utils.debug
import dev.alexzvn.petstats.utils.nextTick
import fr.nocsy.mcpets.data.Pet
import fr.nocsy.mcpets.events.EntityMountPetEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.spigotmc.event.entity.EntityDismountEvent

class PetActionListener : Listener {
    @EventHandler
    fun onMountPet(event: EntityMountPetEvent) {
        if (event.entity !is Player) {
            return
        }

        "Entity Mount pet event".debug()

        if (PetModifierHolder.has(event.pet.id)) {
            PetModifierHolder.bind(event.entity.uniqueId, event.pet.id)
        }
    }

    @EventHandler
    fun onUnmountPet(event: EntityDismountEvent) {
        if (event.entity !is Player) {
            return
        }

        val player = event.entity as Player

        nextTick {
            if (player.isInsideVehicle) {
                return@nextTick
            }

            "Player ${player.name} is unmounted".debug()

            val pet = Pet.getFromEntity(event.entity) ?: return@nextTick

            PetModifierHolder.unbind(player.uniqueId, pet.id)
        }
    }
}