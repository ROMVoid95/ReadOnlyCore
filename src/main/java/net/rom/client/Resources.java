package net.rom.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.rom.Ref;
import net.rom.client.render.Texture;


/**
 * The Class Resources.
 */
@SideOnly(Side.CLIENT)
public class Resources {

	/** The Constant BLANK. */
	public static final Texture BLANK = new Texture(Ref.MODID, "textures/misc/blank.png");
	
	/** The Constant PARTICLES. */
	public static final Texture PARTICLES = new Texture("textures/particle/particles.png");
	
	/** The Constant SKY_SUN. */
	public static final Texture SKY_SUN = new Texture("textures/environment/sun.png");
	
	/** The Constant SKY_RAIN. */
	public static final Texture SKY_RAIN = new Texture("textures/environment/rain.png");
	
	/** The Constant SKY_CLOUDS. */
	public static final Texture SKY_CLOUDS = new Texture("textures/environment/clouds.png");
}