package net.rom.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;


/**
 * The Class BlockStateUtil.
 */
public class BlockStateUtil {
	
	/**
	 * Gets the state.
	 *
	 * @param stack the stack
	 * @return the state
	 */
	public static IBlockState getState(ItemStack stack) {
		Block block = Block.getBlockFromItem(stack.getItem());
		if (block != null && !(block instanceof BlockAir))
			return getState(block, stack.getMetadata());
		return Blocks.AIR.getDefaultState();
	}
	
	/**
	 * Gets the state.
	 *
	 * @param block the block
	 * @param meta the meta
	 * @return the state
	 */
	@SuppressWarnings("deprecation")
	public static IBlockState getState(Block block, int meta) {
		try {
			return block.getStateFromMeta(meta);
		} catch (Exception e) {
			return block.getDefaultState();
		}
	}

}
