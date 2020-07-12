package net.rom.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.rom.client.ClientEvents;


/**
 * The Class LibClientProxy.
 */
public final class LibClientProxy extends LibCommonProxy {
	
	/**
	 * Pre init.
	 *
	 * @param event the event
	 */
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
	}
}