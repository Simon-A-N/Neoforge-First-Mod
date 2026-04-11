package net.supersimon.avatarmod.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class StatsMenu extends AbstractContainerMenu {

    // We will register this ModMenus.STATS_MENU in step 3!
    // It will show as an error in your IDE until then.
    public StatsMenu(int containerId, Inventory playerInventory) {
        super(ModMenus.STATS_MENU.get(), containerId);
    }

    // This handles shift-clicking items. We return EMPTY since we don't have slots yet.
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    // This checks if the player is still allowed to have the menu open.
    // Returning true means it stays open until the player closes it.
    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
