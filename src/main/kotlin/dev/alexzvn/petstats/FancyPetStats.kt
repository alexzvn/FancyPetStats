package dev.alexzvn.petstats

import dev.alexzvn.petstats.listeners.PetActionListener
import dev.alexzvn.petstats.listeners.PlayerActionListener
import dev.alexzvn.petstats.modifier.PetModifierHolder
import dev.alexzvn.petstats.utils.listen
import org.bukkit.plugin.java.JavaPlugin

class FancyPetStats : JavaPlugin() {
    companion object {
        const val name = "FancyPetStats"

        private var instance: FancyPetStats? = null

        fun instance(): FancyPetStats {
            return instance ?: throw IllegalAccessException("TriviaRunner is not initialized")
        }
    }

    init {
        instance = this
    }

    override fun onEnable() {
        saveDefaultConfig()
        PetModifierHolder.load()
        PetModifierHolder.count().also {total ->
            logger.info("Loaded $total section")
        }

        PetActionListener().listen()
        PlayerActionListener().listen()
    }

    override fun onDisable() {
        PetModifierHolder.unload()
    }
}