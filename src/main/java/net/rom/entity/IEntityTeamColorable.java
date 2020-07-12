package net.rom.entity;

import net.minecraft.entity.EntityLiving;


/**
 * An interface used by the
 * 
 * @author CJMinecraft
 * @param <T> The entity class
 */
public interface IEntityTeamColorable<T extends EntityLiving>
{

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	EnumColors getColor();

	/**
	 * Sets the color.
	 *
	 * @param team the team
	 * @return the t
	 */
	T setColor(EnumColors team);

}
