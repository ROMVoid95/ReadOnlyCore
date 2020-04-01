package net.rom.core.space.enums;

import java.util.Comparator;

import org.apache.commons.lang3.Range;

import micdoodle8.mods.galacticraft.api.galaxies.Star;
import net.minecraft.util.IStringSerializable;

public class SpectralStar extends Star {
	
	public enum SpectralType implements IStringSerializable, Comparable<SpectralType> {
		O("O", Integer.MAX_VALUE),
		B("B",25000),
		A("A",10000),
		F("F",7500),
		G("G",6000),
		K("K",5000),
		M("M", 3500);
		
		private final String name;
		private final int tempHigh;
		
		SpectralType(String name, int tempHigh) {
			this.name = name;
			this.tempHigh = tempHigh;
			
		}
		
		public int getHighestTemp() {
			return this.tempHigh;
		}

		public SpectralType getTypeFromTemp(int temp) {

			return null;
		}

		@Override
		public String getName() {
			return this.name;
		}
		
	}
	
	public enum LuminosityClass {
		;
		
		public String getName() {
			return null;
		}
		
	}

	public SpectralStar(String planetName) {
		super(planetName);
	}

}
