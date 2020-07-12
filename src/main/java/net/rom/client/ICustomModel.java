package net.rom.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * The Interface ICustomModel.
 */
public interface ICustomModel {
	
    /**
     * Register models.
     */
    @SideOnly(Side.CLIENT)
    void registerModels();
}
