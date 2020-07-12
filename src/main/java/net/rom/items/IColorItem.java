package net.rom.items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * The Interface IColorItem.
 */
public interface IColorItem {
	
	/**
	 * Gets the color handler.
	 *
	 * @return the color handler
	 */
	@SideOnly(Side.CLIENT)
	IItemColor getColorHandler();

}
