package net.rom.readonlycore.tile;

import net.minecraft.util.EnumFacing;

//TODO: MOVE TO CORE MOD
public interface IRotatableYAxis
{
	public EnumFacing getRotationYAxis();

	public void setRotationYAxis(EnumFacing facing);
}