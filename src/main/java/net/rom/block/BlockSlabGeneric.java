package net.rom.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * The Class BlockSlabGeneric.
 */
public class BlockSlabGeneric extends BlockGeneric {

	/** The Constant PART. */
	public static final PropertyEnum<BlockSlabGeneric.Part> PART = PropertyEnum.<Part>create("part", Part.class);

	/** The Constant AABB_BOTTOM_HALF. */
	public static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);

	/** The Constant AABB_TOP_HALF. */
	public static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

	/** The block type. */
	private final IBlockState blockType;

	/**
	 * Instantiates a new block slab generic.
	 *
	 * @param name      the name
	 * @param blockType the block type
	 */
	public BlockSlabGeneric(IBlockState blockType) {
		super(blockType.getMaterial(), blockType.getMaterial().getMaterialMapColor());
		this.blockType = blockType;
	}

	/**
	 * Gets the map color.
	 *
	 * @param state the state
	 * @param world the world
	 * @param pos   the pos
	 * @return the map color
	 */
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
		return this.blockType.getMapColor(world, pos);
	}

	/**
	 * Can silk harvest.
	 *
	 * @return true, if successful
	 */
	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	/**
	 * Gets the bounding box.
	 *
	 * @param state  the state
	 * @param source the source
	 * @param pos    the pos
	 * @return the bounding box
	 */
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.isDouble(state) ? FULL_BLOCK_AABB
				: (state.getValue(PART) == Part.TOP ? AABB_TOP_HALF : AABB_BOTTOM_HALF);
	}

	/**
	 * Checks if is opaque cube.
	 *
	 * @param state the state
	 * @return true, if is opaque cube
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return this.isDouble(state);
	}

	/**
	 * Does side block rendering.
	 *
	 * @param state the state
	 * @param world the world
	 * @param pos   the pos
	 * @param face  the face
	 * @return true, if successful
	 */
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
			return super.doesSideBlockRendering(state, world, pos, face);

		if (state.isOpaqueCube())
			return true;

		Part side = state.getValue(PART);
		return (side == Part.TOP && face == EnumFacing.UP) || (side == Part.BOTTOM && face == EnumFacing.DOWN);
	}

	/**
	 * Gets the state for placement.
	 *
	 * @param worldIn the world in
	 * @param pos     the pos
	 * @param facing  the facing
	 * @param hitX    the hit X
	 * @param hitY    the hit Y
	 * @param hitZ    the hit Z
	 * @param meta    the meta
	 * @param placer  the placer
	 * @return the state for placement
	 */
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer, placer.getActiveHand())
				.withProperty(PART, Part.BOTTOM);
		return this.isDouble(iblockstate) ? iblockstate
				: (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? iblockstate
						: iblockstate.withProperty(PART, Part.TOP));
	}

	/**
	 * Quantity dropped.
	 *
	 * @param state   the state
	 * @param fortune the fortune
	 * @param random  the random
	 * @return the int
	 */
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return this.isDouble(state) ? 2 : 1;
	}

	/**
	 * Checks if is full cube.
	 *
	 * @param state the state
	 * @return true, if is full cube
	 */
	@Override
	public boolean isFullCube(IBlockState state) {
		return this.isDouble(state);
	}

	/**
	 * Gets the meta from state.
	 *
	 * @param state the state
	 * @return the meta from state
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(PART).getId();
	}

	/**
	 * Gets the state from meta.
	 *
	 * @param meta the meta
	 * @return the state from meta
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(PART, Part.values()[meta % 3]);
	}

	/**
	 * Creates the block state.
	 *
	 * @return the block state container
	 */
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { PART });
	}

	/**
	 * Checks if is half slab.
	 *
	 * @param state the state
	 * @return true, if is half slab
	 */
	@SideOnly(Side.CLIENT)
	private boolean isHalfSlab(IBlockState state) {
		Block block = state.getBlock();
		return block instanceof BlockSlab;
	}

	/**
	 * The Enum Part.
	 */
	public enum Part implements IStringSerializable {

		/** The top. */
		TOP,
		/** The bottom. */
		BOTTOM,
		/** The full. */
		FULL;

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public int getId() {
			return this.ordinal();
		}
	}

	/**
	 * Checks if is double.
	 *
	 * @param state the state
	 * @return true, if is double
	 */
	private boolean isDouble(IBlockState state) {
		return state.getValue(PART) == Part.FULL;
	}

	/**
	 * Gets the block type.
	 *
	 * @return the block type
	 */
	public IBlockState getBlockType() {
		return blockType;
	}

	/**
	 * Sets the block tab.
	 *
	 * @param tab the tab
	 * @return the block slab generic
	 */
	public BlockSlabGeneric setBlockTab(CreativeTabs tab) {
		this.setCreativeTab(tab);
		return this;
	}
}
