package net.rom.client.render;

import java.nio.ByteBuffer;

// TODO: Auto-generated Javadoc
/**
 * The Class Color.
 */
//TODO: MOVE TO CORE MOD
public class Color
{
	
	/** The a. */
	public float r, g, b, a;

	/**
	 * Instantiates a new color.
	 *
	 * @param r the r
	 * @param g the g
	 * @param b the b
	 * @param a the a
	 */
	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Instantiates a new color.
	 *
	 * @param c the c
	 */
	public Color(Color c)
	{
		this(c.r, c.g, c.b, c.a);
	}

	/**
	 * Converts 4 RGBA values into a single hexadecimal color value.
	 * 
	 * @param alpha - Alpha value ranged from 0-255
	 * @param red - Red value ranged from 0-255
	 * @param green - Green value ranged from 0-255
	 * @param blue - Blue value ranged from 0-255
	 * @return Hexadecimal created from the provided RGBA values.
	 */
	public static int createHexadecimal(int alpha, int red, int green, int blue)
	{
		org.lwjgl.util.Color color = new org.lwjgl.util.Color(alpha, red, green, blue);
		ByteBuffer dest = ByteBuffer.allocate(4);

		color.writeRGBA(dest);
		dest.rewind();

		return dest.getInt();
	}

	/**
	 * To hexadecimal.
	 *
	 * @return the int
	 */
	public int toHexadecimal()
	{
		org.lwjgl.util.Color color = new org.lwjgl.util.Color(toIntRange(a), toIntRange(r), toIntRange(g), toIntRange(b));
		ByteBuffer dest = ByteBuffer.allocate(4);

		color.writeRGBA(dest);
		dest.rewind();

		return dest.getInt();
	}

	/**
	 * Convert float color range to integer color range.
	 * 
	 * @param f - Float color range (0.0F - 1.0F)
	 * @return Integer color range (1 - 255)
	 */
	public static int toIntRange(float f)
	{
		return (int) ((f * 255) / 1F);
	}

	/**
	 * Convert integer color range to float color range.
	 * 
	 * @param i - Integer color range (1 - 255)
	 * @return Float color range (0.0F - 1.0F)
	 */
	public static float toFloatRange(int i)
	{
		return (i * 1.0F) / 255;
	}
}