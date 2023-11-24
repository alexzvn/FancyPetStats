package dev.alexzvn.petstats.modifier

object ModifierHolder {

    val permissions = PermissionsModifierHolder
    val pets = PetModifierHolder

    fun load() {
        PermissionsModifierHolder.load()
        PetModifierHolder.load()
    }

    fun unload() {
        PermissionsModifierHolder.unload()
        PetModifierHolder.unload()
    }

    fun reload() {
        unload()
        load()
    }
}