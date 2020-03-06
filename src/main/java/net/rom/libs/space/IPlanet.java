package net.rom.libs.space;

import java.util.ArrayList;

import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;

public interface IPlanet {
	
	String planetName();
	
	SolarSystem solarSystem();
	
	int DimensionId();
	
	int RequiredTier();
	
	float PlanetPhase(); 
	
	float PlanetSize();
	
	float DistanceFromCenter(); 
	
	float RelativeTime();
	
	ArrayList<Biome> biome();
	
	Class<? extends WorldProvider> WorldProvider();

}
