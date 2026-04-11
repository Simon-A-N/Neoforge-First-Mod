package net.supersimon.avatarmod.data;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.supersimon.avatarmod.AvatarMod;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AvatarMod.MODID);

    // This creates an integer stat that starts at 0 and is saved when the player dies
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> JADE_AGILITY =
            ATTACHMENTS.register("jade_agility", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .copyOnDeath()
                    .build());

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }
}