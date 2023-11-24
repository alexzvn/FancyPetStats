package dev.alexzvn.petstats.modifier

import dev.alexzvn.petstats.FancyPetStats
import dev.alexzvn.petstats.utils.warning
import net.Indyuce.mmocore.api.player.PlayerData
import org.bukkit.entity.Player
import java.util.UUID

object PetModifierHolder {

    /**
     * key is name of pet
     */
    private val modifiers = mutableMapOf<String, StatsConfig>()

    /**
     * key is player uuid
     */
    private val clearQueue = mutableMapOf<UUID, Set<StatsConfig>>()


    fun load() {
        modifiers.clear()

        val config = FancyPetStats.instance().config.getConfigurationSection("pets")
            ?: return "Can not find pets config".warning()

        config.getKeys(false).forEach { name: String ->
            config.getConfigurationSection(name)?.let {
                modifiers[name] = StatsConfig(it)
            }
        }
    }

    fun check(player: Player, pet: String): Boolean {
        if (!has(pet)) {
            return true
        }

        val perm = modifiers[pet]!!.getPermission() ?: return true

        return player.hasPermission(perm)
    }

    fun has(name: String): Boolean {
        return modifiers.containsKey(name)
    }

    fun get(name: String): StatsConfig? {
        return modifiers[name]
    }

    private fun queue(player: UUID, stats: StatsConfig) {
        if (! clearQueue.containsKey(player)) {
            clearQueue[player] = setOf(stats)
        }

        clearQueue[player]!!.plus(stats)
    }

    fun count(): Int {
        return modifiers.count()
    }

    fun bind(player: Player, pet: String) {
        bind(player.uniqueId, pet)
    }

    fun bind(player: UUID, pet: String) {
        val mmo = PlayerData.get(player).mmoPlayerData
        val stats = get(pet) ?: return

        queue(player, stats)
        stats.bind(mmo)
    }

    fun unbind(player: Player, pet: String) {
        unbind(player.uniqueId, pet)
    }

    fun unbind(player: UUID, pet: String) {
        val mmo = PlayerData.get(player).mmoPlayerData
        val stats = get(pet) ?: return

        stats.unbind(mmo)
    }

    fun forget(player: UUID) {
        clearQueue[player]?.let { stats ->
            val mmo = PlayerData.get(player).mmoPlayerData

            stats.forEach {
                it.unbind(mmo)
            }
        }

        clearQueue.remove(player)
    }

    fun forget(player: UUID, pet: String) {
        clearQueue[player]?.apply {
            val mmo = PlayerData.get(player).mmoPlayerData
            val stat = find { value -> value.id == pet  } ?: return@apply

            stat.unbind(mmo)
            minus(stat)
        }
    }

    fun unload() {
        clearQueue.forEach { (player, stats) ->
            val mmo = PlayerData.get(player).mmoPlayerData
            stats.forEach {
                it.unbind(mmo)
            }
        }

        clearQueue.clear()
        modifiers.clear()
    }

    fun reload() {
        unload()
        load()
    }
}