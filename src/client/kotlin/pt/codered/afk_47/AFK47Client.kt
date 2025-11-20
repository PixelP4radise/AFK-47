package pt.codered.afk_47

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.minecraft.text.Text

object AFK47Client : ClientModInitializer {
    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->

            // The base command is "/afk"
            dispatcher.register(
                literal("afk")

                    // Subcommand: /afk chop
                    .then(literal("chop").executes { context ->
                        val client = context.source.client
                        client.player?.sendMessage(Text.of("Chop"), false)
                        1 // Return 1 indicates success
                    })

                    // Subcommand: /afk mine
                    .then(literal("mine").executes { context ->
                        val client = context.source.client
                        1
                    })

                    // Subcommand: /afk fish
                    .then(literal("fish").executes { context ->
                        1
                    })

                    // Subcommand: /afk kill
                    .then(literal("kill").executes { context ->
                        1
                    })

                    // Subcommand: /afk stop
                    .then(literal("stop").executes { context ->
                        1
                    })
            )
        }
    }
}