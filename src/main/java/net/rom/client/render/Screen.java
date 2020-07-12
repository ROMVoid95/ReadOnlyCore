package net.rom.client.render;

import java.awt.Dimension;
import java.awt.Point;

import javax.vecmath.Vector2d;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.rom.utils.CoreUtil;

@SideOnly(Side.CLIENT)
public class Screen
{

	/**
	 * Compatibility version of the ScaledResolution class. Returns the current game
	 * display resolution.
	 * 
	 * @return Returns an instance of the compatibility version of ScaledResolution.
	 */
	public static ScaledResolution scaledDisplayResolution()
	{
		return new ScaledResolution(CoreUtil.getMinecraft(), CoreUtil.getMinecraft().displayWidth, CoreUtil.getMinecraft().displayHeight);
	}

	/**
	 * Scaled mouse position.
	 *
	 * @return Returns a Vector2d instance containing the mouse's scaled coordinates
	 *         in-game.
	 */
	public static Vector2d scaledMousePosition()
	{
		final int SCALED_WIDTH = scaledDisplayResolution().getScaledWidth();
		final int SCALED_HEIGHT = scaledDisplayResolution().getScaledHeight();
		final int MOUSE_X = Mouse.getX() * SCALED_WIDTH / CoreUtil.getMinecraft().displayWidth;
		final int MOUSE_Y = SCALED_HEIGHT - Mouse.getY() * SCALED_HEIGHT / CoreUtil.getMinecraft().displayHeight - 1;
		return new Vector2d(MOUSE_X, MOUSE_Y);
	}

	/**
	 * Display resolution.
	 *
	 * @return Returns the current game display width and height as a Dimension
	 */
	public static Dimension displayResolution()
	{
		Minecraft mc = CoreUtil.getMinecraft();
		return new Dimension(mc.displayWidth, mc.displayHeight);
	}

	/**
	 * Gets the mouse location.
	 *
	 * @return Returns the mouse location in-game.
	 */
	public static Point getMouseLocation()
	{
		ScaledResolution size = scaledDisplayResolution();
		Dimension res = displayResolution();
		return new Point(Mouse.getX() * size.getScaledWidth() / res.width, size.getScaledHeight() - Mouse.getY() * size.getScaledHeight() / res.height - 1);
	}

}