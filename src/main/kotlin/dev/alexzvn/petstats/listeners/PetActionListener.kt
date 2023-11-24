package dev.alexzvn.petstats.listeners

import dev.alexzvn.petstats.modifier.PetModifierHolder
import dev.alexzvn.petstats.utils.debug
import dev.alexzvn.petstats.utils.nextTick
import fr.nocsy.mcpets.data.Pet
import fr.nocsy.mcpets.events.EntityMountPetEvent
import fr.nocsy.mcpets.events.PetDespawnEvent
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

        val player = event.entity as Player
        val pet = PetModifierHolder.get(event.pet.id) ?: return

        PetModifierHolder.apply {
            if (has(pet.id) && check(player, pet.id)) {
                bind(player, pet.id)
            }
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

            val pet = Pet.getFromEntity(event.entity) ?: return@nextTick

            PetModifierHolder.unbind(player, pet.id)
        }
    }


    @EventHandler
    fun onDespawnPet(event: PetDespawnEvent) {
        PetModifierHolder.forget(event.pet.owner, event.pet.id)
    }
}