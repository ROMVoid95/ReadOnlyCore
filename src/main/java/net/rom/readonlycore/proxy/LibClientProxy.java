package net.rom.readonlycore.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.rom.readonlycore.client.ClientEvents;

public final class LibClientProxy extends LibCommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
	}
}