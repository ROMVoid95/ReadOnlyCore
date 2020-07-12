package net.rom.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * The Interface IFluid.
 */
public interface IFluid {

	/**
	 * Register models.
	 */
	@SideOnly(Side.CLIENT)
	public void registerModels();

}
