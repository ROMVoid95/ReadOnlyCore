package net.rom.libs.space.world;

import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class StellarBiomeGen extends BiomeStellar {

	public StellarBiomeGen(Biome.BiomeProperties properties) {
		super(properties);
	}

	public void registerTypes(Biome b) {
		if (!ConfigManagerCore.disableBiomeTypeRegistrations)
			BiomeDictionary.addTypes(b, new BiomeDictionary.Type[] { BiomeDictionary.Type.COLD,
					BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD });
	}
}
