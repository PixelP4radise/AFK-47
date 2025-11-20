package pt.codered.afk_47

import baritone.api.BaritoneAPI
import baritone.api.pathing.goals.GoalXZ
import net.minecraft.block.Block

object Baritone {
    private val baritone
        get() = BaritoneAPI.getProvider().primaryBaritone

    fun stop() {
        baritone.pathingBehavior.cancelEverything()
        baritone.mineProcess.cancel()
        baritone.builderProcess.pause()
    }

    fun mine(vararg blocks: Block) {
        baritone.mineProcess.mine(*blocks)
    }

    fun goto(x: Int, z: Int) {
        val goal = GoalXZ(x, z)
        baritone.customGoalProcess.setGoalAndPath(goal)
    }

    fun isActive(): Boolean {
        return baritone.pathingBehavior.isPathing || baritone.mineProcess.isActive
    }
}