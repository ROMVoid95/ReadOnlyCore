package net.rom.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.rom.block.BlockMetaSubtypes;


/**
 * The Class ItemBlockMetaSubtypes.
 */
public class ItemBlockMetaSubtypes extends net.minecraft.item.ItemBlock {
    
    /** The subtype count. */
    private final int subtypeCount;

    /**
     * Instantiates a new item block meta subtypes.
     *
     * @param block the block
     */
    public ItemBlockMetaSubtypes(BlockMetaSubtypes block) {
        this(block, block.getSubtypeCount());
    }

    /**
     * Instantiates a new item block meta subtypes.
     *
     * @param block the block
     * @param subtypeCount the subtype count
     */
    public ItemBlockMetaSubtypes(Block block, int subtypeCount) {
        super(block);
        this.subtypeCount = subtypeCount;
        setMaxDamage(0);
        setHasSubtypes(subtypeCount > 1);
    }

    /**
     * Gets the metadata.
     *
     * @param damage the damage
     * @return the metadata
     */
    @Override
    public int getMetadata(int damage) {
        return damage & 0xF;
    }

    /**
     * Gets the translation key.
     *
     * @param stack the stack
     * @return the translation key
     */
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + getMetadata(stack.getItemDamage());
    }

    /**
     * Gets the sub items.
     *
     * @param tab the tab
     * @param items the items
     */
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) return;
        for (int i = 0; i < this.subtypeCount; ++i)
            items.add(new ItemStack(this, 1, i));
    }
}
