package net.supersimon.avatarmod.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.supersimon.avatarmod.AvatarMod;
import net.supersimon.avatarmod.network.StatsMenuPacket;

// Notice there is NO "bus = MOD" here. This means it listens to live game events!
@EventBusSubscriber(modid = AvatarMod.MODID, value = Dist.CLIENT)
public class KeyInputHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        // If 'J' is pressed and the game isn't paused...
        if (ModKeybinds.OPEN_STATS.consumeClick()) {
            // Send our custom packet to the Server!
            PacketDistributor.sendToServer(new StatsMenuPacket());
        }
    }
}
