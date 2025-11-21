package pt.codered.afk_47.afk_modules

import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d

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

    override fun onChat(client: MinecraftClient, message: Text) {
        println("Got unfiltered message: $message")
    }

    override fun onSystemMessage(client: MinecraftClient, message: Text) {
        println("Got unfiltered system message: $message")
    }
}