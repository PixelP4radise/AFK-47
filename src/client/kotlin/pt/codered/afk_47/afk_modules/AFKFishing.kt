package pt.codered.afk_47.afk_modules

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.math.Box
import pt.codered.afk_47.utils.ChatUtils
import pt.codered.afk_47.utils.TimeUtils

enum class FishingState { CASTING, WAITING_FOR_BITE, REELING, IDLE_AFTER_CATCH }

object AFKFishing : AFKModule() {
    var state = FishingState.CASTING
    var stateTimer = 0


    override fun onEnable(client: MinecraftClient) {
        //reset variables
        //save position
    }

    //TODO: future work saving both the bobber and the entity of !!! to save some compute time

    //tenho que so decrementar o cast delay quando nao ha bobber na agua
    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return
        val world = client.world ?: return
        val interactionManager = client.interactionManager ?: return

        if (!isHoldingRod(player)) return

        val range = 25.0
        val box = Box.of(player.pos, range * 2, range * 2, range * 2)

        val nearbyEntities = world.getOtherEntities(player, box)

        if (stateTimer > 0) {
            stateTimer--
            return
        }

        when (state) {
            FishingState.CASTING -> {
                interactionManager.interactItem(player, Hand.MAIN_HAND)
                player.swingHand(Hand.MAIN_HAND)

                state = FishingState.WAITING_FOR_BITE
                stateTimer = TimeUtils.getRandomDelay(20, 35)
            }

            FishingState.WAITING_FOR_BITE -> {
                if (!isBobberOut(nearbyEntities)) {
                    state = FishingState.CASTING
                    return
                }

                if (isReadyToFish(nearbyEntities)) {
                    stateTimer = TimeUtils.getHumanReaction(6.0, 1.5)
                    state = FishingState.REELING
                }
            }

            FishingState.REELING -> {
                interactionManager.interactItem(player, Hand.MAIN_HAND)
                player.swingHand(Hand.MAIN_HAND)
                state = FishingState.IDLE_AFTER_CATCH
                stateTimer = TimeUtils.getRandomDelay(20, 35)
            }

            FishingState.IDLE_AFTER_CATCH -> {
                state = FishingState.CASTING
            }
        }
    }


    override fun onSystemMessage(client: MinecraftClient, message: Text) {
        if (ChatUtils.isDefenseMessage(message) || ChatUtils.isManaMessage(message)) return
        println("Got unfiltered system message: ${message.string}")
    }

    override fun onChat(client: MinecraftClient, message: Text) {
        println("Got chat message: ${message.string}")
    }

    private fun isHoldingRod(player: ClientPlayerEntity): Boolean {
        return player.mainHandStack.item == Items.FISHING_ROD
    }

    private fun isBobberOut(entities: List<Entity>): Boolean {
        entities.forEach { entity ->
            val type = entity.type

            if (type == EntityType.FISHING_BOBBER) {
                return true
            }
        }
        return false
    }

    private fun isReadyToFish(entities: List<Entity>): Boolean {
        return entities.any { entity ->
            val name = entity.displayName?.string ?: entity.name.string
            name.contains("!!!") // Use contains just in case there are color codes
        }
    }
}