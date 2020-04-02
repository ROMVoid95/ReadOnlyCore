package net.rom.core.space.implemtations.star;

import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import net.rom.core.space.calculations.SpectralClassifcation;
import net.rom.core.space.enums.SpectralClass;

public class ExoStar extends Star implements IExoStar {
	
	private SpectralClass spectralClass;
	private String starName;
	private SolarSystem starSystem;
	private int surfaceTemp;
	private double radius;
	private double mass;
	

	public ExoStar(String starName) {
		super(starName);
	}
	
	public ExoStar setStarName(String starName) {
		this.starName = starName;
		return this;
	}

	@Override
	public String getStarName() {
		return this.starName;
	}

	@Override
	public SolarSystem getStarSystem() {
		return this.starSystem;
	}

	@Override
	public int getSurfaceTemp() {
		return this.surfaceTemp;
	}

	@Override
	public double getStellarRadius() {
		return this.radius;
	}

	@Override
	public SpectralClass getSpectralClassifcation() {
		return this.spectralClass;
	}

	@Override
	public double getMass() {
		return this.mass;
	}


}
