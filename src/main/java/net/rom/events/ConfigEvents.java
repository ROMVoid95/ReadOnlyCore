package net.rom.events;

import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.rom.Ref;


/**
 * The Class ConfigEvents.
 */
@Mod.EventBusSubscriber(modid = Ref.MODID)
public class ConfigEvents {
	
	/**
	 * On config changed.
	 *
	 * @param event the event
	 */
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent event) {
		if (event.getModID().equals(Ref.MODID))
			ConfigManager.sync(Ref.MODID, Type.INSTANCE);
	}
}
