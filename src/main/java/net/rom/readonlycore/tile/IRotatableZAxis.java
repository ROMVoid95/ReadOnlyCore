package net.rom.readonlycore.tile;

import net.minecraft.util.EnumFacing;

//TODO: MOVE TO CORE MOD
public interface IRotatableZAxis
{
	public EnumFacing getRotationZAxis();

	public void setRotationZAxis(EnumFacing facing);
}