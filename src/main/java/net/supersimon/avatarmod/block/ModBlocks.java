package net.supersimon.avatarmod.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.supersimon.avatarmod.AvatarMod;
import net.supersimon.avatarmod.item.ModItems;

import java.util.Properties;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AvatarMod.MODID);

    public static final DeferredBlock<Block> JADE_BLOCK = registerBlock("jade_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> JADE_ORE =registerBlock("jade_ore",
            () -> new DropExperienceBlock(UniformInt.of(4, 6),
                    BlockBehaviour.Properties.of()
                            .strength(2f).requiresCorrectToolForDrops().sound(SoundType.STONE)));




    private static <T extends Block>  DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
     DeferredBlock<T> toReturn = BLOCKS.register(name, block);
     registerBlockItem(name, toReturn);
     return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.Items.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

     public static void register(IEventBus eventBus) {
         BLOCKS.register(eventBus);
     }
}
