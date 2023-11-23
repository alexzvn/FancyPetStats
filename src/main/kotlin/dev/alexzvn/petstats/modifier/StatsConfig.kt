package dev.alexzvn.petstats.modifier

import dev.alexzvn.petstats.FancyPetStats
import dev.alexzvn.petstats.utils.warning
import io.lumine.mythic.lib.api.player.EquipmentSlot
import io.lumine.mythic.lib.api.player.MMOPlayerData
import io.lumine.mythic.lib.api.stat.modifier.StatModifier
import io.lumine.mythic.lib.player.modifier.ModifierSource
import io.lumine.mythic.lib.player.modifier.ModifierType
import org.bukkit.configuration.ConfigurationSection

class StatsConfig(section: ConfigurationSection) {
    private val stats = mutableMapOf<String, Double>()
    private var modifiers = listOf<StatModifier>()

    init {
        read(section)
        modifiers = stats.map { (key, value) ->
            StatModifier(
                FancyPetStats.name,
                key,
                value,
                ModifierType.FLAT,
                EquipmentSlot.OTHER,
                ModifierSource.OTHER
            )
        }
    }

    private fun read(section: ConfigurationSection) {
        val config =  section.getConfigurationSection("stats")
            ?: return "Can not find stats config of ${section.name}".warning()

        config.getKeys(false).forEach {key ->
            stats[key] = section.getDouble(key)
        }

        modifiers = getModifiers()
    }

    fun getStats(): Map<String, Double> {
        return stats
    }

    fun getModifiers(): List<StatModifier> {
        return modifiers
    }
    
    fun bind(player: MMOPlayerData) {
        modifiers.forEach {
            it.register(player)
        }
    }

    fun unbind(player: MMOPlayerData) {
        modifiers.forEach {
            it.unregister(player)
        }
    }
}
