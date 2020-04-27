package net.rom.core.proxy;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.rom.core.autoreg.ReadonlyReg;

public interface IProxy {
    void preInit(ReadonlyReg registry, FMLPreInitializationEvent event);

    void init(ReadonlyReg registry, FMLInitializationEvent event);

    void postInit(ReadonlyReg registry, FMLPostInitializationEvent event);
    
    @Nullable EntityPlayer getClientPlayer();
}
