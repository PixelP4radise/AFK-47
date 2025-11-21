package pt.codered.afk_47.utils

import net.minecraft.text.Text

object ChatUtils {
    fun isDefenseMessage(message: Text): Boolean {
        val content = message.string.lowercase()
        return content.contains("defense")
    }

    fun isManaMessage(message: Text): Boolean {
        val content = message.string.lowercase()
        return content.contains("mana")
    }
}