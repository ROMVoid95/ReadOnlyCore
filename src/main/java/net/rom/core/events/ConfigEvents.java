package net.rom.core.events;

import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.rom.core.ReadOnlyCore;

@Mod.EventBusSubscriber(modid = ReadOnlyCore.MODID)
public class ConfigEvents {
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent event) {
		if (event.getModID().equals(ReadOnlyCore.MODID))
			ConfigManager.sync(ReadOnlyCore.MODID, Type.INSTANCE);
	}
}
