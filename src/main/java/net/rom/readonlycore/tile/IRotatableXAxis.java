package net.rom.readonlycore.tile;

import net.minecraft.util.EnumFacing;

//TODO: MOVE TO CORE MOD
public interface IRotatableXAxis
{
	public EnumFacing getRotationXAxis();

	public void setRotationXAxis(EnumFacing facing);
}