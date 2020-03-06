package net.rom.libs.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.rom.libs.autoreg.StellarRegistry;

public interface IProxy {
    void preInit(StellarRegistry registry, FMLPreInitializationEvent event);

    void init(StellarRegistry registry, FMLInitializationEvent event);

    void postInit(StellarRegistry registry, FMLPostInitializationEvent event);
}