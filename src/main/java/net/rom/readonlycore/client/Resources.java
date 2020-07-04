package net.rom.readonlycore.client;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.rom.readonlycore.Ref;
import net.rom.readonlycore.client.render.Texture;

@SideOnly(Side.CLIENT)
public class Resources {

	public static final Texture BLANK = new Texture(Ref.MODID, "textures/misc/blank.png");
	public static final Texture PARTICLES = new Texture("textures/particle/particles.png");
	public static final Texture SKY_SUN = new Texture("textures/environment/sun.png");
	public static final Texture SKY_RAIN = new Texture("textures/environment/rain.png");
	public static final Texture SKY_CLOUDS = new Texture("textures/environment/clouds.png");
}