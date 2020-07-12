package net.rom.tile;

import net.minecraft.util.EnumFacing;


/**
 * The Interface IRotatableZAxis.
 */
//TODO: MOVE TO CORE MOD
public interface IRotatableZAxis
{
	
	/**
	 * Gets the rotation Z axis.
	 *
	 * @return the rotation Z axis
	 */
	public EnumFacing getRotationZAxis();

	/**
	 * Sets the rotation Z axis.
	 *
	 * @param facing the new rotation Z axis
	 */
	public void setRotationZAxis(EnumFacing facing);
}