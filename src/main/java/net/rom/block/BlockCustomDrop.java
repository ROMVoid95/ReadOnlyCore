package net.rom.block;

import java.util.Arrays;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;


/**
 * The Class BlockCustomDrop.
 */
public class BlockCustomDrop extends BlockGeneric {
	
	/** The dropped items. */
	private final ItemStack[] droppedItems;

	/**
	 * Instantiates a new block custom drop.
	 *
	 * @param material the material
	 * @param drops the drops
	 */
	public BlockCustomDrop(Material material, ItemStack... drops) {
		super(material);
		this.droppedItems = drops;
	}

	/**
	 * Instantiates a new block custom drop.
	 *
	 * @param material the material
	 * @param color the color
	 * @param drops the drops
	 */
	public BlockCustomDrop(Material material, MapColor color, ItemStack... drops) {
		super(material, color);
		this.droppedItems = drops;
	}

	/**
	 * Gets the drops.
	 *
	 * @param drops the drops
	 * @param world the world
	 * @param pos the pos
	 * @param state the state
	 * @param fortune the fortune
	 */
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		if (droppedItems != null) {
			drops.addAll(Arrays.asList(droppedItems));
		}
	}
}