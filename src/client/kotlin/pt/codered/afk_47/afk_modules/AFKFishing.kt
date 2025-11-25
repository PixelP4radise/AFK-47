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

object AFKFishing : AFKModule() {
    var stateTimer = 0

    // --- STATE VARIABLES ---
    private var castDelay = 0           // Cooldown between casts
    private var reactionTimer = 0       // Human reaction delay

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

        if (!isBobberOut(nearbyEntities)) {
            if (castDelay > 0) castDelay--
            else if (castDelay == 0) {
                interactionManager.interactItem(player, Hand.MAIN_HAND)
                player.swingHand(Hand.MAIN_HAND)
                castDelay = TimeUtils.getRandomDelay(30, 50)
            }
        }

        if (!isReadyToFish(nearbyEntities)) return
        else {
            if (reactionTimer > 0) reactionTimer--

            if (reactionTimer == 0) {
                interactionManager.interactItem(player, Hand.MAIN_HAND)
                player.swingHand(Hand.MAIN_HAND)

                reactionTimer = TimeUtils.getHumanReaction(7.0, 2.0)
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