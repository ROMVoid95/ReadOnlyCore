package net.rom.libs.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomModel {
	
    @SideOnly(Side.CLIENT)
    void registerModels();
}
