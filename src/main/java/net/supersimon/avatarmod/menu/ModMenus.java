package net.supersimon.avatarmod.menu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.supersimon.avatarmod.AvatarMod;

public class ModMenus {
    // This creates the registry for all your custom menus
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, AvatarMod.MODID);

    // This registers your specific StatsMenu
    public static final DeferredHolder<MenuType<?>, MenuType<StatsMenu>> STATS_MENU =
            MENUS.register("stats_menu", () -> IMenuTypeExtension.create((windowId, inv, data) -> new StatsMenu(windowId, inv)));

    // We will call this from your main mod class
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}