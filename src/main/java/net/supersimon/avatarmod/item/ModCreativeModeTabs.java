package net.supersimon.avatarmod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.supersimon.avatarmod.AvatarMod;
import net.supersimon.avatarmod.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AvatarMod.MODID);

    public static final Supplier<CreativeModeTab> AVATAR_ITEMS_TAB = CREATIVE_MODE_TAB.register("avatar_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BISMUTH.get()))
                    .title(Component.translatable("creativetab.avamod.avatar_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.BISMUTH);
                        output.accept(ModItems.RAW_BISMUTH);


                    }).build());

    public static final Supplier<CreativeModeTab> AVATAR_BLOCKS_TAB = CREATIVE_MODE_TAB.register("avatar_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.BISMUTH_BLOCK.get()))
                    //Next line is what allows multiple tabs
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AvatarMod.MODID, "avatar_items_tab"))
                    .title(Component.translatable("creativetab.avamod.avatar_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.BISMUTH_BLOCK);


                    }).build());




    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
