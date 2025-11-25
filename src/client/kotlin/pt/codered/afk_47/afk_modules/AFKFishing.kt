package pt.codered.afk_47.afk_modules

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.math.Box
import pt.codered.afk_47.utils.ChatUtils

object AFKFishing : AFKModule() {


    override fun onEnable(client: MinecraftClient) {
        //reset variables
        //save position
    }

    override fun onTick(client: MinecraftClient) {
        val player = client.player ?: return
        val world = client.world ?: return

        if (!isHoldingRod(player)) return

        val range = 25.0
        val box = Box.of(player.pos, range * 2, range * 2, range * 2)

        val nearbyEntities = world.getOtherEntities(player, box)

        if (!isBobberOut(nearbyEntities)) {
            //send cane after the delay from the reel in
        }

        //if yes wait for entity with name ? save then check when entity name becomes !!!
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
}