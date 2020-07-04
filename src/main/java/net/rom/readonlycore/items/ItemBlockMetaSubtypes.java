package net.rom.readonlycore.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.rom.readonlycore.block.BlockMetaSubtypes;

public class ItemBlockMetaSubtypes extends net.minecraft.item.ItemBlock {
    private final int subtypeCount;

    public ItemBlockMetaSubtypes(BlockMetaSubtypes block) {
        this(block, block.getSubtypeCount());
    }

    public ItemBlockMetaSubtypes(Block block, int subtypeCount) {
        super(block);
        this.subtypeCount = subtypeCount;
        setMaxDamage(0);
        setHasSubtypes(subtypeCount > 1);
    }

    @Override
    public int getMetadata(int damage) {
        return damage & 0xF;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey() + getMetadata(stack.getItemDamage());
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) return;
        for (int i = 0; i < this.subtypeCount; ++i)
            items.add(new ItemStack(this, 1, i));
    }
}
