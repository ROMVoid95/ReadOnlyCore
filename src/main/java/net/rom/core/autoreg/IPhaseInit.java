package net.rom.core.autoreg;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * A singleton that has preInit, init, and postInit methods. If registered in the ReadonlyRegistry, these are called
 * automatically.
 *
 */
public interface IPhaseInit {
	
    default void preInit(ReadonlyReg registry, FMLPreInitializationEvent event) {
    }

    default void init(ReadonlyReg registry, FMLInitializationEvent event) {
    }

    default void postInit(ReadonlyReg registry, FMLPostInitializationEvent event) {
    }

}
