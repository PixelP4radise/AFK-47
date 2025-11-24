package pt.codered.afk_47.afk_modules

import net.minecraft.client.MinecraftClient
import net.minecraft.item.Items
import net.minecraft.text.Text
import pt.codered.afk_47.utils.ChatUtils

object AFKFishing : AFKModule() {


    override fun onEnable(client: MinecraftClient) {
        //reset variables
        //save position
    }

    override fun onTick(client: MinecraftClient) {
        if (!isHoldingRod(client)) return
        //check if player is holding a cane
        //check if bobber is already on water
        //if not send cane after delay from reel in
        //if yes wait for entity with name ? save then check when entity name becomes !!!
    }


    override fun onSystemMessage(client: MinecraftClient, message: Text) {
        if (ChatUtils.isDefenseMessage(message) || ChatUtils.isManaMessage(message)) return
        println("Got unfiltered system message: ${message.string}")
    }

    override fun onChat(client: MinecraftClient, message: Text) {
        println("Got chat message: ${message.string}")
    }

    private fun isHoldingRod(client: MinecraftClient): Boolean {
        val player = client.player ?: return false
        return player.mainHandStack.item != Items.FISHING_ROD
    }
}