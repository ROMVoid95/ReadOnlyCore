package net.rom.proxy;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.rom.registry.ReadOnlyRegistry;


/**
 * The Interface IProxy.
 */
public interface IProxy {
    
    /**
     * Pre init.
     *
     * @param registry the registry
     * @param event the event
     */
    void preInit(ReadOnlyRegistry registry, FMLPreInitializationEvent event);

    /**
     * Inits the.
     *
     * @param registry the registry
     * @param event the event
     */
    void init(ReadOnlyRegistry registry, FMLInitializationEvent event);

    /**
     * Post init.
     *
     * @param registry the registry
     * @param event the event
     */
    void postInit(ReadOnlyRegistry registry, FMLPostInitializationEvent event);
    
    /**
     * Gets the client player.
     *
     * @return the client player
     */
    @Nullable EntityPlayer getClientPlayer();
}
