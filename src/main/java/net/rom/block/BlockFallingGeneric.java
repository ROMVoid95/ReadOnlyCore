package net.rom.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;


/**
 * The Class BlockFallingGeneric.
 */
//TODO: MOVE TO CORE MOD, AND make this generically named.
public class BlockFallingGeneric extends BlockFalling {

	/** The map color. */
	private final MapColor mapColor;
	
	/** The dust color. */
	private final int dustColor;

	/**
	 * Instantiates a new block falling generic.
	 *
	 * @param material the material
	 * @param dustColor the dust color
	 */
	public BlockFallingGeneric(Material material, int dustColor) {
		super(material);
		this.mapColor = material.getMaterialMapColor();
		this.dustColor = dustColor;
	}

	/**
	 * Instantiates a new block falling generic.
	 *
	 * @param material the material
	 * @param color the color
	 * @param dustColor the dust color
	 */
	public BlockFallingGeneric(Material material, MapColor color, int dustColor) {
		super(material);
		this.mapColor = color;
		this.dustColor = dustColor;
	}

	/**
	 * Sets the sound type.
	 *
	 * @param sound the sound
	 * @return the block falling generic
	 */
	@Override
	public BlockFallingGeneric setSoundType(SoundType sound) {
		return (BlockFallingGeneric) super.setSoundType(sound);
	}

	/**
	 * Sets or removes the tool and level required to harvest this block.
	 *
	 * @param toolClass Class
	 * @param level     Harvest level: Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
	 * @return the block falling generic
	 */
	public BlockFallingGeneric setBlockHarvestLevel(String toolClass, int level) {
		setHarvestLevel(toolClass, level);
		return this;
	}

	/**
	 * Sets or removes the tool and level required to harvest this block.
	 *
	 * @param toolClass Class
	 * @param level     Harvest level: Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
	 * @param state     The specific state.
	 * @return the block falling generic
	 */
	public BlockFallingGeneric setBlockHarvestLevel(String toolClass, int level, IBlockState state) {
		setHarvestLevel(toolClass, level, state);
		return this;
	}

	/**
	 * Gets the map color.
	 *
	 * @param state the state
	 * @param worldIn the world in
	 * @param pos the pos
	 * @return the map color
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return mapColor;
	}

	/**
	 * Gets the dust color.
	 *
	 * @param state the state
	 * @return the dust color
	 */
	@Override
	public int getDustColor(IBlockState state) {
		return dustColor;
	}
}