package dev.alexzvn.petstats.utils

import dev.alexzvn.petstats.FancyPetStats
import net.luckperms.api.LuckPerms
import net.luckperms.api.event.LuckPermsEvent
import net.luckperms.api.event.user.UserLoadEvent
import org.bukkit.Bukkit

fun withLuckperms(callback: (api: LuckPerms) -> Unit) {
    Bukkit.getServicesManager().getRegistration(LuckPerms::class.java)?.run {
        provider.apply(callback)
    }
}

fun <T: LuckPermsEvent> subscribeLuckpermEvent(event: Class<T>, callback: (event: T) -> Unit) {
    withLuckperms {
        it.eventBus.subscribe(FancyPetStats.instance(), event, callback)
    }
}