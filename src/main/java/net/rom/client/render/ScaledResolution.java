package net.rom.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TODO: Auto-generated Javadoc
/**
 * The Class ScaledResolution.
 */
//TODO: MOVE TO CORE MOD
@SideOnly(Side.CLIENT)
public class ScaledResolution
{
	
	/** The scaled width. */
	private int scaledWidth;
	
	/** The scaled height. */
	private int scaledHeight;
	
	/** The scaled width D. */
	private double scaledWidthD;
	
	/** The scaled height D. */
	private double scaledHeightD;
	
	/** The scale factor. */
	private int scaleFactor;

	/**
	 * Instantiates a new scaled resolution.
	 *
	 * @param mc the mc
	 * @param width the width
	 * @param height the height
	 */
	public ScaledResolution(Minecraft mc, int width, int height)
	{
		this.scaledWidth = width;
		this.scaledHeight = height;
		this.scaleFactor = 1;
		boolean flag = mc.getLanguageManager().isCurrentLocaleUnicode() || mc.gameSettings.forceUnicodeFont;
		int scale = mc.gameSettings.guiScale;

		if (scale == 0)
		{
			scale = 1000;
		}

		while (this.scaleFactor < scale && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240)
		{
			++this.scaleFactor;
		}

		if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1)
		{
			--this.scaleFactor;
		}

		this.scaledWidthD = (double) this.scaledWidth / (double) this.scaleFactor;
		this.scaledHeightD = (double) this.scaledHeight / (double) this.scaleFactor;
		this.scaledWidth = MathHelper.ceil(this.scaledWidthD);
		this.scaledHeight = MathHelper.ceil(this.scaledHeightD);
	}

	/**
	 * Gets the scaled width.
	 *
	 * @return the scaled width
	 */
	public int getScaledWidth()
	{
		return this.scaledWidth;
	}

	/**
	 * Gets the scaled height.
	 *
	 * @return the scaled height
	 */
	public int getScaledHeight()
	{
		return this.scaledHeight;
	}

	/**
	 * Gets the scaled width double.
	 *
	 * @return the scaled width double
	 */
	public double getScaledWidth_double()
	{
		return this.scaledWidthD;
	}

	/**
	 * Gets the scaled height double.
	 *
	 * @return the scaled height double
	 */
	public double getScaledHeight_double()
	{
		return this.scaledHeightD;
	}

	/**
	 * Gets the scale factor.
	 *
	 * @return the scale factor
	 */
	public int getScaleFactor()
	{
		return this.scaleFactor;
	}
}