package pt.codered.afk_47

import net.minecraft.client.MinecraftClient
import pt.codered.afk_47.afk_modules.AFKModule
import pt.codered.afk_47.afk_modules.EntityInspector

object AFKManager {
    val modules = listOf<AFKModule>(
        EntityInspector
    )

    fun onTick(client: MinecraftClient) {
        // Only run the modules that are currently enabled
        modules.filter { it.isEnabled }.forEach { it.onTick(client) }
    }

    fun disableAll(client: MinecraftClient) {
        modules.forEach { it.disable(client) }
    }
}