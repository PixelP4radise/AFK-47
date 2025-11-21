package pt.codered.afk_47

import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import pt.codered.afk_47.afk_modules.AFKFishing
import pt.codered.afk_47.afk_modules.AFKModule
import pt.codered.afk_47.afk_modules.EntityInspector

object AFKManager {
    val modules = listOf<AFKModule>(
        EntityInspector,
        AFKFishing
    )

    fun onTick(client: MinecraftClient) {
        // Only run the modules that are currently enabled
        modules.filter { it.isEnabled }.forEach { it.onTick(client) }
    }

    fun disableAll(client: MinecraftClient) {
        modules.forEach { it.disable(client) }
    }

    fun onChat(client: MinecraftClient, message: Text) {
        // Pass the message to enabled modules
        modules.filter { it.isEnabled }.forEach { it.onChat(client, message) }
    }

    fun onSystemMessage(client: MinecraftClient, message: Text) {
        modules.filter { it.isEnabled }.forEach { it.onSystemMessage(client, message) }
    }
}