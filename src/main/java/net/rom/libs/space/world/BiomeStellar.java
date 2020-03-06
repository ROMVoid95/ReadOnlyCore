package net.rom.libs.space.world;

import micdoodle8.mods.galacticraft.api.world.BiomeGenBaseGC;
import net.minecraft.world.biome.Biome;

public abstract class BiomeStellar extends BiomeGenBaseGC  {
	  public static Biome ACSpace = (Biome)new StellarBiomeGen((new Biome.BiomeProperties("Stellar")).setBaseHeight(1.0F).setHeightVariation(0.5F).setRainfall(0.0F));
	  
	  public static Biome ACSpaceLowPlains = (Biome)new StellarBiomeGen((new Biome.BiomeProperties("StellarPlainsLow")).setBaseHeight(0.125F).setHeightVariation(0.5F).setRainfall(0.0F));
	  
	  public static Biome ACSpaceMidPlains = (Biome)new StellarBiomeGen((new Biome.BiomeProperties("StellarPlainsMid")).setBaseHeight(0.2F).setHeightVariation(0.5F).setRainfall(0.0F));
	  
	  public static Biome ACSpaceLowHills = (Biome)new StellarBiomeGen((new Biome.BiomeProperties("StellarHillsLow")).setBaseHeight(0.45F).setHeightVariation(0.5F).setRainfall(0.0F));
	  
	  public static Biome ACSpaceMidHills = (Biome)new StellarBiomeGen((new Biome.BiomeProperties("StellarPlainsMid")).setBaseHeight(1.0F).setHeightVariation(0.5F).setRainfall(0.0F));
	  
	  public byte topMeta;
	  
	  public byte fillerMeta;
	  
	  public byte stoneMeta;
	  
	  public BiomeStellar(Biome.BiomeProperties properties) {
	    super(properties);
	  }
	  
	  public float getSpawningChance() {
	    return 0.1F;
	  }
}
