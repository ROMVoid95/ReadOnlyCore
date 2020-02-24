package net.rom.libs.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.rom.libs.utils.EnumerationHelper;

public class ItemBlockMeta<E extends Enum<E> & IStringSerializable> extends ItemBlock {
	private E[] values;

	/**
	 * Create a new meta item block using the different values of the given enum
	 * class
	 * 
	 * @param enumClass
	 *            The enum class containing the different meta values
	 * @param block
	 *            The block which the {@link ItemBlock} will use
	 */
	public ItemBlockMeta(final Class<E> enumClass, Block block) {
		super(block);
		this.values = EnumerationHelper.getEnumValues(enumClass);
		setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			for (E value : this.values)
				items.add(new ItemStack(this, 1, value.ordinal()));
		}
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack stack) {
		return this.getUnlocalizedName() + "."
				+ this.values[Math.min(stack.getItemDamage(), this.values.length - 1)].getName();
	}
}
