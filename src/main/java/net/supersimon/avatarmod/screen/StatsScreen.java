package net.supersimon.avatarmod.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.supersimon.avatarmod.AvatarMod;
import net.supersimon.avatarmod.menu.StatsMenu;

public class StatsScreen extends AbstractContainerScreen<StatsMenu> {

    // The updated 1.21 ResourceLocation syntax
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AvatarMod.MODID, "textures/gui/stats_menu.png");

    public StatsScreen(StatsMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // Adds a "+" button next to the stat text
        this.addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal("+"), button -> {
            // When clicked, send the packet to the server
            net.neoforged.neoforge.network.PacketDistributor.sendToServer(new net.supersimon.avatarmod.network.UpgradeStatPacket());
        }).bounds(x + 140, y + 20, 20, 20).build());
    }

    // This is the method Java was complaining about missing!
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, 8, 8, 0x404040, false);
        guiGraphics.drawString(this.font, Component.literal("Jade Mastery: 0"), 8, 25, 0x00FF00, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}