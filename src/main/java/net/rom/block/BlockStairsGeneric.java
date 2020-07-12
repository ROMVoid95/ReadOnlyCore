package net.rom.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;


/**
 * The Class BlockStairsGeneric.
 */
public class BlockStairsGeneric extends BlockStairs {

	/** The type. */
	private RegistryType type;
	
	/** The item. */
	private ItemBlock item;

	/**
	 * Instantiates a new block stairs generic.
	 *
	 * @param material the material
	 */
	public BlockStairsGeneric(IBlockState material) {
		super(material);
	}

	/**
	 * Sets the sound type.
	 *
	 * @param sound the sound
	 * @return the block stairs generic
	 */
	@Override
	public BlockStairsGeneric setSoundType(SoundType sound) {
		return (BlockStairsGeneric) super.setSoundType(sound);
	}

	/**
	 * Sets or removes the tool and level required to harvest this block.
	 *
	 * @param toolClass Class
	 * @param level     Harvest level: Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
	 * @return the block stairs generic
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
	 * @return the block stairs generic
	 */
	public BlockStairsGeneric setBlockHarvestLevel(String toolClass, int level, IBlockState state) {
		setHarvestLevel(toolClass, level, state);
		return this;
	}

	/**
	 * Sets the item block.
	 *
	 * @param item the item
	 * @return the block stairs generic
	 */
	public BlockStairsGeneric setItemBlock(ItemBlock item) {
		this.item = item;
		return this;
	}
}