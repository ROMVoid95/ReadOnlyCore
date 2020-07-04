package net.rom.readonlycore.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;

public class BlockStairsGeneric extends BlockStairs {

	private RegistryType type;
	private ItemBlock item;

	public BlockStairsGeneric(IBlockState material) {
		super(material);
	}

	@Override
	public BlockStairsGeneric setSoundType(SoundType sound) {
		return (BlockStairsGeneric) super.setSoundType(sound);
	}

	/**
	 * Sets or removes the tool and level required to harvest this block.
	 *
	 * @param toolClass Class
	 * @param level     Harvest level: Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
	 */
	public BlockStairsGeneric setBlockHarvestLevel(String toolClass, int level) {
		setHarvestLevel(toolClass, level);
		return this;
	}

	/**
	 * Sets or removes the tool and level required to harvest this block.
	 *
	 * @param toolClass Class
	 * @param level     Harvest level: Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
	 * @param state     The specific state.
	 */
	public BlockStairsGeneric setBlockHarvestLevel(String toolClass, int level, IBlockState state) {
		setHarvestLevel(toolClass, level, state);
		return this;
	}

	public BlockStairsGeneric setItemBlock(ItemBlock item) {
		this.item = item;
		return this;
	}
}