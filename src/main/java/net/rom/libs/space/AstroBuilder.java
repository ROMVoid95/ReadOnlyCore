package net.rom.libs.space;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.Satellite;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.world.gen.BiomeOrbit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;

/**
 * The Class AstroBuilder.
 */
public class AstroBuilder {

	/**
	 * Register solar system.
	 *
	 * @param prefix   the prefix
	 * @param name     the name
	 * @param galaxy   the galaxy
	 * @param pos      the pos
	 * @param starname the starname
	 * @param size     the size
	 * @return the solar system
	 */
	public static SolarSystem registerSolarSystem(String prefix, String name, String galaxy, Vector3 pos,
			String starname, float size) {
		SolarSystem body = new SolarSystem(name, galaxy);
		body.setMapPosition(pos);
		Star main = new Star(starname);
		main.setParentSolarSystem(body);
		main.setTierRequired(-1);
		main.setRelativeSize(size);
		main.setBodyIcon(
				new ResourceLocation(prefix, "textures/gui/celestialbodies/" + name + "/" + starname + ".png"));
		body.setMainStar(main);
		return body;
	}

	/**
	 * Register planet.
	 *
	 * @param system             the system
	 * @param name               the name
	 * @param prefix             the prefix
	 * @param provider           the provider
	 * @param dimID              the dim ID
	 * @param tier               the tier
	 * @param phase              the phase
	 * @param size               the size
	 * @param distancefromcenter the distancefromcenter
	 * @param relativetime       the relativetime
	 * @param biome              the biome
	 * @return the planet
	 */
	public static Planet registerPlanet(SolarSystem system, String name, String prefix,
			Class<? extends WorldProvider> provider, int dimID, int tier, float phase, float size,
			float distancefromcenter, float relativetime, Biome... biome) {
		Planet body = (new Planet(name)).setParentSolarSystem(system);
		String path = system.getUnlocalizedName().replace("solarsystem.", "");
		body.setRingColorRGB(0.0F, 0.4F, 0.9F);
		body.setPhaseShift(phase);
		body.setRelativeSize(size);
		body.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(distancefromcenter, distancefromcenter));
		body.setRelativeOrbitTime(relativetime);
		body.setBodyIcon(new ResourceLocation(prefix, "textures/gui/celestialbodies/" + path + "/" + name + ".png"));
		if (provider != null) {
			body.setTierRequired(tier);
			body.setDimensionInfo(dimID, provider);
			if (biome != null)
				body.setBiomeInfo(biome);
		}
		return body;
	}

	/**
	 * Register moon.
	 *
	 * @param parent             the parent
	 * @param name               the name
	 * @param prefix             the prefix
	 * @param provider           the provider
	 * @param dimID              the dim ID
	 * @param tier               the tier
	 * @param phase              the phase
	 * @param size               the size
	 * @param distancefromcenter the distancefromcenter
	 * @param relativetime       the relativetime
	 * @param biome              the biome
	 * @return the moon
	 */
	public static Moon registerMoon(Planet parent, String name, String prefix, Class<? extends WorldProvider> provider,
			int dimID, int tier, float phase, float size, float distancefromcenter, float relativetime,
			Biome... biome) {
		Moon body = (new Moon(name)).setParentPlanet(parent);
		String path = parent.getParentSolarSystem().getUnlocalizedName().replace("solarsystem.", "");
		body.setRingColorRGB(0.0F, 0.4F, 0.9F);
		body.setPhaseShift(phase);
		body.setRelativeSize(size);
		body.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(distancefromcenter, distancefromcenter));
		body.setRelativeOrbitTime(relativetime);
		body.setBodyIcon(
				new ResourceLocation(prefix, "textures/gui/celestialbodies/" + path + "/moons/" + name + ".png"));
		if (provider != null) {
			body.setTierRequired(tier);
			body.setDimensionInfo(dimID, provider);
			if (biome != null)
				body.setBiomeInfo(biome);
		}
		return body;
	}

	/**
	 * Register satellite.
	 *
	 * @param parent             the parent
	 * @param prefix             the prefix
	 * @param provider           the provider
	 * @param dimID              the dim ID
	 * @param dimIDStatic        the dim ID static
	 * @param phase              the phase
	 * @param size               the size
	 * @param distancefromcenter the distancefromcenter
	 * @param relativetime       the relativetime
	 * @return the satellite
	 */
	public static Satellite registerSatellite(Planet parent, String prefix, Class<? extends WorldProvider> provider,
			int dimID, int dimIDStatic, float phase, float size, float distancefromcenter, float relativetime) {
		Satellite body = new Satellite("spacestation." + parent.getUnlocalizedName().replace("planet.", ""));
		body.setParentBody(parent);
		body.setRelativeOrbitTime(relativetime);
		body.setPhaseShift(phase);
		body.setRelativeSize(size);
		body.setRingColorRGB(0.0F, 0.4F, 0.9F);
		body.setBodyIcon(new ResourceLocation("galacticraftcore:textures/gui/celestialbodies/space_station.png"));
		if (provider != null) {
			body.setTierRequired(parent.getTierRequirement());
			body.setDimensionInfo(dimID, dimIDStatic, provider);
			body.setBiomeInfo(new Biome[] { BiomeOrbit.space });
		}
		return body;
	}

	/**
	 * Calculate gravity.
	 *
	 * @param Si the si
	 * @return the float
	 */
	public static float calculateGravity(float Si) {
		float i = (9.81F - Si) * 0.008664628F;
		float k = Math.round(i * 1000.0F);
		return k / 1000.0F;
	}
}
