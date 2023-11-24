package dev.alexzvn.petstats

import dev.alexzvn.petstats.listeners.PermissionChangeListener
import dev.alexzvn.petstats.listeners.PetActionListener
import dev.alexzvn.petstats.listeners.PlayerActionListener
import dev.alexzvn.petstats.modifier.ModifierHolder
import dev.alexzvn.petstats.modifier.PetModifierHolder
import dev.alexzvn.petstats.utils.listen
import dev.alexzvn.petstats.utils.withLuckperms
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
        ModifierHolder.load()

        PetActionListener().listen()
        PlayerActionListener().listen()
        PermissionChangeListener().listen()
    }

    override fun onDisable() {
        ModifierHolder.unload()
    }
}