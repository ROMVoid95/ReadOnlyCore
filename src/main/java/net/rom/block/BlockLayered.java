package net.rom.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * <em><b>Copyright (c) 2018 The Starcraft Minecraft (SCMC) Mod Team.</b></em>
 * <br>
 * 
 * The base for each block that can be layered like snow.
 */
public abstract class BlockLayered extends BlockGeneric {

	/** The Constant LAYERS. */
	public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);
	
	/** The Constant LAYERS_AABB. */
	protected static final AxisAlignedBB[] LAYERS_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };

	/**
	 * Instantiates a new block layered.
	 *
	 * @param material the material
	 */
	public BlockLayered(Material material) {
		this(material, material.getMaterialMapColor());
	}

	/**
	 * Instantiates a new block layered.
	 *
	 * @param material the material
	 * @param color the color
	 */
	public BlockLayered(Material material, MapColor color) {
		super(material, color);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LAYERS, Integer.valueOf(1)));
		this.setTickRandomly(true);
	}

	/**
	 * Can place block at.
	 *
	 * @param worldIn the world in
	 * @param pos the pos
	 * @return true, if successful
	 */
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos.down());
		Block block = iblockstate.getBlock();
		return block != Blocks.ICE && block != Blocks.PACKED_ICE
				? (iblockstate.getBlock().isLeaves(iblockstate, worldIn,
						pos.down())
								? true
								: (block == this && iblockstate.getValue(LAYERS).intValue() >= 7 ? true
										: iblockstate.isOpaqueCube() && iblockstate.getMaterial().blocksMovement()))
				: false; // FUCK
							// RIGHT
							// OFF,
							// VALIEC
	}

	/**
	 * Creates the block state.
	 *
	 * @return the block state container
	 */
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { LAYERS });
	}

	/**
	 * Gets the bounding box.
	 *
	 * @param state the state
	 * @param source the source
	 * @param pos the pos
	 * @return the bounding box
	 */
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return LAYERS_AABB[state.getValue(LAYERS).intValue()];
	}

	/**
	 * Gets the collision bounding box.
	 *
	 * @param blockState the block state
	 * @param worldIn the world in
	 * @param pos the pos
	 * @return the collision bounding box
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		int i = blockState.getValue(LAYERS).intValue() - 1;
		AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
		return new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX,
				i * 0.125F, axisalignedbb.maxZ);
	}

	/**
	 * Gets the item dropped.
	 *
	 * @param state the state
	 * @param rand the rand
	 * @param fortune the fortune
	 * @return the item dropped
	 */
	@Nullable
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}

	/**
	 * Gets the meta from state.
	 *
	 * @param state the state
	 * @return the meta from state
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(LAYERS).intValue() - 1;
	}

	/**
	 * Gets the state from meta.
	 *
	 * @param meta the meta
	 * @return the state from meta
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(LAYERS, Integer.valueOf((meta & 7) + 1));
	}

	/**
	 * Harvest block.
	 *
	 * @param worldIn the world in
	 * @param player the player
	 * @param pos the pos
	 * @param state the state
	 * @param te the te
	 * @param stack the stack
	 */
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state,
			@Nullable TileEntity te, @Nullable ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockToAir(pos);
	}

	/**
	 * Checks if is full cube.
	 *
	 * @param state the state
	 * @return true, if is full cube
	 */
	@Override
	public boolean isFullCube(IBlockState state) {
		return state.getValue(LAYERS).intValue() == 7;
	}

	/**
	 * Checks if is opaque cube.
	 *
	 * @param state the state
	 * @return true, if is opaque cube
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	/**
	 * Checks if is passable.
	 *
	 * @param worldIn the world in
	 * @param pos the pos
	 * @return true, if is passable
	 */
	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getValue(LAYERS).intValue() < 5;
	}

	/**
	 * Checks if is replaceable.
	 *
	 * @param worldIn the world in
	 * @param pos the pos
	 * @return true, if is replaceable
	 */
	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getValue(LAYERS).intValue() == 1;
	}

	/**
	 * Neighbor changed.
	 *
	 * @param state the state
	 * @param world the world
	 * @param pos the pos
	 * @param block the block
	 * @param fromPos the from pos
	 */
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		this.checkAndDropBlock(world, pos, state);
	}

	/**
	 * Quantity dropped.
	 *
	 * @param state the state
	 * @param fortune the fortune
	 * @param random the random
	 * @return the int
	 */
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return (state.getValue(LAYERS)) + 1;
	}

	/**
	 * Should side be rendered.
	 *
	 * @param blockState the block state
	 * @param blockAccess the block access
	 * @param pos the pos
	 * @param side the side
	 * @return true, if successful
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
			EnumFacing side) {
		if (side == EnumFacing.UP) {
			return true;
		} else {
			IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
			return iblockstate.getBlock() == this
					&& iblockstate.getValue(LAYERS).intValue() >= blockState.getValue(LAYERS).intValue() ? true
							: super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	/**
	 * Check and drop block.
	 *
	 * @param worldIn the world in
	 * @param pos the pos
	 * @param state the state
	 * @return true, if successful
	 */
	private boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!canPlaceBlockAt(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
			return false;
		} else {
			return true;
		}
	}
}
