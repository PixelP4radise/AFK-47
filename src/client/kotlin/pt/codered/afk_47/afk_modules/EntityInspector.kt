package pt.codered.afk_47.afk_modules

import net.minecraft.client.MinecraftClient
import net.minecraft.entity.LivingEntity
import net.minecraft.text.Text
import net.minecraft.util.math.Box

object EntityInspector : AFKModule() {
    private var cooldown = 0

    override fun onTick(client: MinecraftClient) {
        if (cooldown > 0) {
            cooldown--
            return
        }

        val player = client.player ?: return
        player.sendMessage(Text.of("Estou a funcioanr"), false)
        val world = client.world ?: return
        val range = 7.0

        val box = Box.of(player.pos, range * 2, range * 2, range * 2)

        val nearbyEntities = world.getOtherEntities(player, box)

        nearbyEntities.forEach { entity ->
            val name = entity.displayName?.string ?: entity.name.string
            val type = entity.type.toString().replace("entity.minecraft.", "")
            val dist = "%.1f".format(player.distanceTo(entity))
            val pos = "%.1f, %.1f, %.1f".format(entity.x, entity.y, entity.z)
            val uuid = entity.uuidAsString.substring(0, 8)

            var stats = ""
            if (entity is LivingEntity) {
                val hp = "%.1f".format(entity.health)
                val maxHp = "%.1f".format(entity.maxHealth)
                val armor = entity.armor
                stats = " | §cHP: $hp/$maxHp §r| §9Armor: $armor"
            }

            client.player?.sendMessage(Text.of("Scan de $name do tipo $type com uuid $uuid"), false)
        }

        cooldown = 20
    }

    override fun onEnable(client: MinecraftClient) {
        cooldown = 0
    }
}