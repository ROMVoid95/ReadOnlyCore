package net.rom.tile;

import net.minecraft.util.EnumFacing;


/**
 * The Interface IRotatableYAxis.
 */
//TODO: MOVE TO CORE MOD
public interface IRotatableYAxis
{
	
	/**
	 * Gets the rotation Y axis.
	 *
	 * @return the rotation Y axis
	 */
	public EnumFacing getRotationYAxis();

	/**
	 * Sets the rotation Y axis.
	 *
	 * @param facing the new rotation Y axis
	 */
	public void setRotationYAxis(EnumFacing facing);
}