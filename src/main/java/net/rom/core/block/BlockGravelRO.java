package net.rom.core.block;

import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockGravelRO extends BlockFalling {
	public BlockGravelRO(String name) {
		super();
		this.setHardness(0.6F);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.FLINT;
	}
}
