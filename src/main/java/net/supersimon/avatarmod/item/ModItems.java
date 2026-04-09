package net.supersimon.avatarmod.item;


import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.supersimon.avatarmod.AvatarMod;

public class ModItems {
    public static final DeferredRegister.Items Items = DeferredRegister.createItems(AvatarMod.MODID);
// green strings like "bismuth" must ve lowercase and <Item> things must be uppercase
    public static final DeferredItem<Item> JADE = Items.register("jade",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UNREFINED_JADE = Items.register( "unrefined_jade",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        Items.register(eventBus);
    }
}

