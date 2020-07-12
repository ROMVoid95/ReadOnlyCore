package net.rom.tile;

import net.minecraft.util.EnumFacing;


/**
 * The Interface IRotatableXAxis.
 */
//TODO: MOVE TO CORE MOD
public interface IRotatableXAxis
{
	
	/**
	 * Gets the rotation X axis.
	 *
	 * @return the rotation X axis
	 */
	public EnumFacing getRotationXAxis();

	/**
	 * Sets the rotation X axis.
	 *
	 * @param facing the new rotation X axis
	 */
	public void setRotationXAxis(EnumFacing facing);
}