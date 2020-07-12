package net.rom.entity;

import java.awt.Color;

import net.minecraft.util.IStringSerializable;


/**
 * This holds all the possible team colors for entities.
 * 
 * @author Ocelot5836
 */
public enum EnumColors implements IStringSerializable{
	
	/** white. */
	WHITE("White", 238, 237, 233),
	
	/** orange. */
	ORANGE("Orange",228, 113, 10),
	
	/** magenta. */
	MAGENTA("Magenta",172, 62, 166),
	
	/** light blue. */
	LIGHT_BLUE("LightBlue",143, 211, 230),
	
	/** yellow. */
	YELLOW("Yellow",231, 204, 54),
	
	/** lime. */
	LIME("Lime",93, 234, 68),
	
	/** pink. */
	PINK("Pink",216, 147, 178),
	
	/** gray. */
	GRAY("Gray",70, 70, 70),
	
	/** silver. */
	SILVER("Silver",187, 187, 191),
	
	/** cyan. */
	CYAN("Cyan",67, 181, 216),
	
	/** purple. */
	PURPLE("Purple",153, 50, 204),
	
	/** blue. */
	BLUE("Blue",56, 95, 183),
	
	/** brown. */
	BROWN("Brown",117, 77, 43),
	
	/** green. */
	GREEN("Green",52, 122, 29),
	
	/** red. */
	RED("Red",168, 21, 24),
	
	/** black. */
	BLACK("Black",25, 25, 25);

	/** name. */
	private String name;
	
	/** r. */
	private float r;
	
	/** g. */
	private float g;
	
	/** b. */
	private float b;

	/**
	 * Instantiates a new enum colors.
	 *
	 * @param name the name
	 * @param r the r
	 * @param g the g
	 * @param b the b
	 */
	EnumColors(String name, float r, float g, float b) {
		this.name = name;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return new Color(r, g, b);
	}

	/**
	 * Gets the r.
	 *
	 * @return the r
	 */
	public float getR() {
		return r;
	}

	/**
	 * Gets the g.
	 *
	 * @return the g
	 */
	public float getG() {
		return g;
	}

	/**
	 * Gets the b.
	 *
	 * @return the b
	 */
	public float getB() {
		return b;
	}

	/**
	 * Gets the team color using ids.
	 *
	 * @param id The id of the color
	 * @return the color by id
	 */
	public static EnumColors getColorById(int id) {
		return EnumColors.values()[id];
	}

	/**
	 * Gets the team color using metadata.
	 *
	 * @param id The id of the color
	 * @return the color by meta
	 */
	public static EnumColors getColorByMeta(int id) {
		return EnumColors.values()[EnumColors.values().length - id];
	}
}