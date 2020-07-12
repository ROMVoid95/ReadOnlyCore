package net.rom.block;

import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;


/**
 * The Class BlockGravelRO.
 */
public class BlockGravelRO extends BlockFalling {
	
	/**
	 * Instantiates a new block gravel RO.
	 *
	 * @param name the name
	 */
	public BlockGravelRO(String name) {
		super();
		this.setHardness(0.6F);
	}

	/**
	 * Gets the item dropped.
	 *
	 * @param state the state
	 * @param rand the rand
	 * @param fortune the fortune
	 * @return the item dropped
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.FLINT;
	}
}
