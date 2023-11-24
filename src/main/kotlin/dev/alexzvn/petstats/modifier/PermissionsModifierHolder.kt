package dev.alexzvn.petstats.modifier

import dev.alexzvn.petstats.FancyPetStats
import dev.alexzvn.petstats.utils.warning
import net.Indyuce.mmocore.api.player.PlayerData

object PermissionsModifierHolder {
    /**
     * key is name of pet
     */
    val modifiers = mutableMapOf<String, StatsConfig>()

    fun load() {
        modifiers.clear()

        val config = FancyPetStats.instance().config.getConfigurationSection("permissions")
            ?: return "Can not find pets config".warning()

        config.getKeys(false).forEach { name: String ->
            config.getConfigurationSection(name)?.let {
                modifiers[name] = StatsConfig(it)
            }
        }
    }

    fun unload() {
        modifiers.clear()
    }
}