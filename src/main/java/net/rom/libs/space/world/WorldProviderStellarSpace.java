package net.rom.libs.space.world;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.world.IExitHeight;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;

public abstract class WorldProviderStellarSpace extends WorldProviderSpace implements IGalacticraftWorldProvider, IExitHeight{

		static long calculateDayLength(float trueTime) {
		return (long) (24000L * trueTime);
		
	}

}
  