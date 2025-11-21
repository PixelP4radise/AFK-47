package pt.codered.afk_47.afk_modules

import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d
import pt.codered.afk_47.utils.ChatUtils

object AFKFishing : AFKModule() {
    private var anchorPos: Vec3d? = null
    private var anchorYaw: Float = 0f
    private var anchorPitch: Float = 0f


    override fun onEnable(client: MinecraftClient) {
        val player = client.player ?: return

        anchorPos = player.pos
        anchorYaw = player.yaw
        anchorPitch = player.pitch
    }

    override fun onTick(client: MinecraftClient) {

    }

    override fun onSystemMessage(client: MinecraftClient, message: Text) {
        if (ChatUtils.isDefenseMessage(message) || ChatUtils.isManaMessage(message)) return
        println("Got unfiltered system message: ${message.string}")
    }
}