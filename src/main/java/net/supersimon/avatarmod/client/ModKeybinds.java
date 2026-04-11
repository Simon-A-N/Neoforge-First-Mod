package net.supersimon.avatarmod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.supersimon.avatarmod.AvatarMod;
import org.lwjgl.glfw.GLFW;

// "bus = MOD" means this loads while the game is booting up
@EventBusSubscriber(modid = AvatarMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeybinds {
    public static final KeyMapping OPEN_STATS = new KeyMapping(
            "key.avamod.open_stats", // The translation key for your lang file
            KeyConflictContext.IN_GAME, // Only works while actively playing
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_J, // Default key is J
            "category.avamod.keys" // The category in the Minecraft controls menu
    );

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(OPEN_STATS);
    }
}