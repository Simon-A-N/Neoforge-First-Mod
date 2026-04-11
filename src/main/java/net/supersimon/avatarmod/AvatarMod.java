package net.supersimon.avatarmod;

import net.supersimon.avatarmod.block.ModBlocks;
import net.supersimon.avatarmod.data.ModAttachments;
import net.supersimon.avatarmod.item.ModCreativeModeTabs;
import net.supersimon.avatarmod.item.ModItems;
import net.supersimon.avatarmod.menu.ModMenus;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AvatarMod.MODID)
public class AvatarMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "avamod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    public AvatarMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // --- Register the networking listener for your Keybind ---
        modEventBus.addListener(this::registerNetworking);

        ModCreativeModeTabs.register(modEventBus);
        ModAttachments.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenus.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // --- The networking method that catches the packets and runs the logic ---
    private void registerNetworking(final net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent event) {
        final net.neoforged.neoforge.network.registration.PayloadRegistrar registrar = event.registrar(MODID);

        // 1. Packet to open the Stats Menu
        registrar.playToServer(
                net.supersimon.avatarmod.network.StatsMenuPacket.TYPE,
                net.supersimon.avatarmod.network.StatsMenuPacket.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        context.player().openMenu(new net.minecraft.world.SimpleMenuProvider(
                                (id, inv, player) -> new net.supersimon.avatarmod.menu.StatsMenu(id, inv),
                                net.minecraft.network.chat.Component.literal("Stats")
                        ));
                    });
                }
        );

        // 2. NEW: Packet to Upgrade Jade Agility and Apply Speed Boost
        registrar.playToServer(
                net.supersimon.avatarmod.network.UpgradeStatPacket.TYPE,
                net.supersimon.avatarmod.network.UpgradeStatPacket.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        net.minecraft.world.entity.player.Player player = context.player();

                        // A. Get current stat and increase it by 1
                        int currentAgility = player.getData(net.supersimon.avatarmod.data.ModAttachments.JADE_AGILITY);
                        int newAgility = currentAgility + 1;
                        player.setData(net.supersimon.avatarmod.data.ModAttachments.JADE_AGILITY, newAgility);

                        // B. Apply the actual Minecraft movement speed modifier
                        net.minecraft.world.entity.ai.attributes.AttributeInstance speedAttribute =
                                player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED);

                        if (speedAttribute != null) {
                            // Fully qualified ResourceLocation to prevent any import errors
                            net.minecraft.resources.ResourceLocation speedModId = net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(MODID, "agility_boost");
                            speedAttribute.removeModifier(speedModId); // Remove old boost

                            // Default player speed is ~0.1. Adding 0.005 per level is a noticeable but balanced boost.
                            speedAttribute.addPermanentModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                                    speedModId,
                                    newAgility * 0.005,
                                    net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE
                            ));
                        }
                    });
                }
        );
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example ingredient item to the Ingredients tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.JADE);
            event.accept(ModItems.UNREFINED_JADE);
        }

        if (event.getTabKey() ==CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.JADE_BLOCK);
            event.accept(ModBlocks.JADE_ORE);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = AvatarMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {

        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

        // --- The correct NeoForge 1.21 way to register screens ---
        @SubscribeEvent
        static void registerScreens(net.neoforged.neoforge.client.event.RegisterMenuScreensEvent event) {
            event.register(
                    ModMenus.STATS_MENU.get(),
                    net.supersimon.avatarmod.screen.StatsScreen::new
            );
        }
    }
}