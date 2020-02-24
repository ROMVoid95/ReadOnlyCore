package net.rom.libs.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.rom.libs.utils.EnumerationHelper;

public class ItemMeta<E extends Enum<E> & IStringSerializable> extends Item {
	private E[] values;

	/**
	 * Create a new meta item using the different values of the given enum class
	 * 
	 * @param enumClass
	 *            The enum class containing the different meta values
	 */
	public ItemMeta(final Class<E> enumClass) {
		this.values = EnumerationHelper.getEnumValues(enumClass);
		setHasSubtypes(true);
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
