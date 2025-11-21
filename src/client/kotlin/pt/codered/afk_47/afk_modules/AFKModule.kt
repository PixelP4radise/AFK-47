package pt.codered.afk_47.afk_modules

import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

abstract class AFKModule {
    var isEnabled = false
        private set

    fun enable(client: MinecraftClient) {
        isEnabled = true
        onEnable(client)
    }

    fun disable(client: MinecraftClient) {
        isEnabled = false
        onDisable(client)
    }

    open fun onEnable(client: MinecraftClient) {}
    open fun onDisable(client: MinecraftClient) {}
    open fun onTick(client: MinecraftClient) {}
    open fun onChat(client: MinecraftClient, message: Text) {}
}