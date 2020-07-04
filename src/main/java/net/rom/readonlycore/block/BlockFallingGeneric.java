package net.rom.readonlycore.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

//TODO: MOVE TO CORE MOD, AND make this generically named.
public class BlockFallingGeneric extends BlockFalling {

	private ItemBlock item;

	private final MapColor mapColor;
	private final int dustColor;

	public BlockFallingGeneric(Material material, int dustColor) {
		super(material);
		this.mapColor = material.getMaterialMapColor();
		this.dustColor = dustColor;
	}

	public BlockFallingGeneric(Material material, MapColor color, int dustColor) {
		super(material);
		this.mapColor = color;
		this.dustColor = dustColor;
	}

	public void setNames(String name) {
		this.setTranslationKey(name);
		this.setRegistryName(name);
	}

	@Override
	public BlockFallingGeneric setSoundType(SoundType sound) {
		return (BlockFallingGeneric) super.setSoundType(sound);
	}

	/**
	 * Sets or removes the tool and level required to harvest this block.
	 *
	 * @param toolClass Class
	 * @param level     Harvest level: Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
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
	 */
	public BlockFallingGeneric setBlockHarvestLevel(String toolClass, int level, IBlockState state) {
		setHarvestLevel(toolClass, level, state);
		return this;
	}

	public BlockFallingGeneric setItemBlock(ItemBlock item) {
		this.item = item;
		return this;
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return mapColor;
	}

	@Override
	public int getDustColor(IBlockState state) {
		return dustColor;
	}
}