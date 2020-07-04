package net.rom.readonlycore.block;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockGeneric extends Block {

	public static final int BIT_NO_METADATA = 0x01;

	protected ItemBlock item;
	protected boolean noMeta;

	public BlockGeneric(Material material) {
		super(material, material.getMaterialMapColor());
	}

	public BlockGeneric(Material material, MapColor color) {
		super(material, color);
	}

	public BlockGeneric(String name, Material material) {
		this(name, material, material.getMaterialMapColor());
	}

	public BlockGeneric(String name, Material material, int modifiers) {
		this(name, material);
		setModifiers(modifiers);
	}

	public BlockGeneric(String name, Material material, MapColor color) {
		super(material, color);
		this.setNames(name);
	}

	public BlockGeneric(String name, Material material, MapColor color, int modifiers) {
		this(name, material, color);
		setModifiers(modifiers);
	}

	private void setModifiers(int modifiers) {
		this.noMeta = (modifiers & BIT_NO_METADATA) > 0;
	}

	@Nullable
	public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end) {
		List<RayTraceResult> list = Lists.<RayTraceResult>newArrayList();
		List<AxisAlignedBB> boxes = Lists.<AxisAlignedBB>newArrayList();
		this.getCollisionBoxes(blockState, world, pos, boxes);

		for (AxisAlignedBB axisalignedbb : boxes) {
			if (axisalignedbb != NULL_AABB) {
				list.add(this.rayTrace(pos, start, end, axisalignedbb));
			}
		}

		RayTraceResult raytraceresult1 = null;
		double d1 = 0.0D;

		for (RayTraceResult raytraceresult : list) {
			if (raytraceresult != null) {
				double d0 = raytraceresult.hitVec.squareDistanceTo(end);

				if (d0 > d1) {
					raytraceresult1 = raytraceresult;
					d1 = d0;
				}
			}
		}

		return raytraceresult1;
	}

	/**
	 * Gets the boxes of the block.
	 * 
	 * @param state The actual state of the block
	 * @param world The world the block is in
	 * @param pos   The position of the block
	 * @param boxes The list to add the boxes to
	 */
	protected void getCollisionBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes) {
		boxes.add(state.getBoundingBox(world, pos));
	}

	public void setNames(String name) {
		this.setTranslationKey(name);
		this.setRegistryName(name);
	}

	@Override
	public BlockGeneric setSoundType(SoundType sound) {
		return (BlockGeneric) super.setSoundType(sound);
	}

	/**
	 * Sets or removes the tool and level required to harvest this block.
	 *
	 * @param toolClass Class
	 * @param level     Harvest level: Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
	 */
	public BlockGeneric setBlockHarvestLevel(String toolClass, int level) {
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
	public BlockGeneric setBlockHarvestLevel(String toolClass, int level, IBlockState state) {
		setHarvestLevel(toolClass, level, state);
		return this;
	}

	public BlockGeneric setItemBlock(ItemBlock item) {
		this.item = item;
		return this;
	}
}
