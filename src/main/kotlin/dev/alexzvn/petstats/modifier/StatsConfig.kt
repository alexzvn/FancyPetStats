package dev.alexzvn.petstats.modifier

import dev.alexzvn.petstats.FancyPetStats
import dev.alexzvn.petstats.utils.debug
import dev.alexzvn.petstats.utils.warning
import io.lumine.mythic.lib.api.player.EquipmentSlot
import io.lumine.mythic.lib.api.player.MMOPlayerData
import io.lumine.mythic.lib.api.stat.modifier.StatModifier
import io.lumine.mythic.lib.player.modifier.ModifierSource
import io.lumine.mythic.lib.player.modifier.ModifierType
import org.bukkit.configuration.ConfigurationSection

class StatsConfig(private val section: ConfigurationSection) {
    private val stats = mutableMapOf<String, Double>()
    private val modifiers: List<StatModifier>
    val id: String = section.name

    init {

        section.getConfigurationSection("stats")?.also {
            it.getKeys(false).forEach {key ->
                stats[key] = it.getDouble(key)
            }
        }

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

    fun getPermission(): String? {
        return section.getString("permission")
    }

    fun unbind(player: MMOPlayerData) {
        modifiers.forEach {
            it.unregister(player)
        }
    }
}
