package pt.codered.afk_47

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.text.Text
import pt.codered.afk_47.afk_modules.EntityInspector

object AFK47Client : ClientModInitializer {
    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        registerCommands()

        ClientTickEvents.END_CLIENT_TICK.register { client ->
            if (client.player == null || client.world == null) return@register

            AFKManager.onTick(client)
        }
    }

    private fun registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal("afk")
                    .then(literal("inspect").executes {
                        EntityInspector.enable(it.source.client)
                        1
                    })
                    .then(literal("stop").executes {
                        val client = it.source.client

                        Baritone.stop()

                        AFKManager.disableAll(client)

                        client.player?.sendMessage(Text.of("§c[AFK-47] STOPPED ALL"), false)

                        1
                    })
            )
        }
    }
}