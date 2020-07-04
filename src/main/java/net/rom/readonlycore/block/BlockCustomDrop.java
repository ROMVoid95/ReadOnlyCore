package net.rom.readonlycore.block;

import java.util.Arrays;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCustomDrop extends BlockGeneric {
	private final ItemStack[] droppedItems;

	public BlockCustomDrop(Material material, ItemStack... drops) {
		super(material);
		this.droppedItems = drops;
	}

	public BlockCustomDrop(String name, Material material, MapColor color, ItemStack... drops) {
		super(name, material, color);
		this.droppedItems = drops;
	}

	public BlockCustomDrop(Material material, MapColor color, ItemStack... drops) {
		super(material, color);
		this.droppedItems = drops;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		if (droppedItems != null) {
			drops.addAll(Arrays.asList(droppedItems));
		}
	}
}