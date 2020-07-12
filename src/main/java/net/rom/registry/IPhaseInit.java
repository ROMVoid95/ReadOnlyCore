package net.rom.registry;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


/**
 * A singleton that has preInit, init, and postInit methods. If registered in the StellarRegistry, these are called
 * automatically.
 *
 */
public interface IPhaseInit {
	
    /**
     * Pre init.
     *
     * @param registry the registry
     * @param event the event
     */
    default void preInit(ReadOnlyRegistry registry, FMLPreInitializationEvent event) {
    }

    /**
     * Inits the.
     *
     * @param registry the registry
     * @param event the event
     */
    default void init(ReadOnlyRegistry registry, FMLInitializationEvent event) {
    }

    /**
     * Post init.
     *
     * @param registry the registry
     * @param event the event
     */
    default void postInit(ReadOnlyRegistry registry, FMLPostInitializationEvent event) {
    }

}
