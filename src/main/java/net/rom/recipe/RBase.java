package net.rom.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;


/**
 * The Class RBase.
 */
public abstract class RBase extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    
    /**
     * Can fit.
     *
     * @param width the width
     * @param height the height
     * @return true, if successful
     */
    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    /**
     * Checks if is dynamic.
     *
     * @return true, if is dynamic
     */
    @Override
    public boolean isDynamic() {
        return true;
    }

    /**
     * Gets the remaining items.
     *
     * @param inv the inv
     * @return the remaining items
     */
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && !stack.getItem().hasContainerItem(stack)) {
                stack.shrink(1);
                inv.setInventorySlotContents(i, stack);
            }
        }
        return NonNullList.create();
    }

    /**
     * Convenience method to make iterating a bit cleaner.
     *
     * @param inv the inv
     * @return A list of all non-empty stacks in the inventory
     */
    public static NonNullList<ItemStack> getNonEmptyStacks(InventoryCrafting inv) {
        NonNullList<ItemStack> list = NonNullList.create();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                list.add(stack);
            }
        }
        return list;
    }
}
