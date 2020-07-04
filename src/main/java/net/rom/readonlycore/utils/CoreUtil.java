package net.rom.readonlycore.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoreUtil {

	public static int nextID = 0;
	private static boolean deobfuscated;
	public static boolean langDisable;
	private static MinecraftServer serverCached;
	private static final Map<String, ResourceLocation> TEXTURES = new HashMap<String, ResourceLocation>();

	static {
		try {
			deobfuscated = Launch.classLoader.getClassBytes("net.minecraft.world.World") != null;
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isDeobfuscated() {
		return deobfuscated;
	}

	public static Side getEffectiveSide() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER
				|| Thread.currentThread().getName().startsWith("Netty Epoll Server IO")) {
			return Side.SERVER;
		}

		return Side.CLIENT;
	}

	public static MinecraftServer getServer() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		if (server == null) {
			return serverCached;
		}
		return server;
	}

	public static Minecraft getClient() {
		return FMLClientHandler.instance().getClient();
	}

	/**
	 * @return The minecraft instance
	 */
	@SideOnly(Side.CLIENT)
	public static Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}

	/**
	 * @return The minecraft font renderer instance
	 */
	@SideOnly(Side.CLIENT)
	public static FontRenderer getFontRenderer() {
		return getMinecraft().fontRenderer;
	}

	/**
	 * @return The percentage from last update and this update
	 */
	@SideOnly(Side.CLIENT)
	public static float getPartialTicks() {
		return getMinecraft().getRenderPartialTicks();
	}

	public static BufferedImage getImageResourceAsStream(ResourceLocation r) {
		try {
			return TextureUtil
					.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(r).getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Determine if tooltips should be calculated, call in
	 * {@link net.minecraftforge.event.entity.player.ItemTooltipEvent} handlers.
	 * This can prevent tooltip events from being processed at unnecessary times
	 * (world loading/closing), while still allowing JEI to build its cache. JEI
	 * tooltip caches are done in {@link LoaderState#AVAILABLE}, in-game is
	 * {@link LoaderState#SERVER_STARTED}.
	 */
	public static boolean shouldCalculateTooltip() {
		LoaderState state = Loader.instance().getLoaderState();
		return state != LoaderState.INITIALIZATION && state != LoaderState.SERVER_ABOUT_TO_START
				&& state != LoaderState.SERVER_STOPPING;
	}

	/**
	 * Binds the specified texture.
	 * 
	 * @param texture The texture to bind
	 */
	public static void bindTexture(ResourceLocation texture) {
		getMinecraft().getTextureManager().bindTexture(texture);
	}

	/**
	 * Binds a texture using the specified domain and path.
	 * 
	 * @param domain The domain of the texture
	 * @param path   The path to the texture
	 */
	public static void bindTexture(String domain, String path) {
		String locationString = domain + ":" + path;
		TEXTURES.computeIfAbsent(locationString, key -> new ResourceLocation(key));
		getMinecraft().getTextureManager().bindTexture(TEXTURES.get(locationString));
	}

	/**
	 * Binds a texture using specified location.
	 * 
	 * @param path The location to the texture
	 */
	public static void bindTexture(String path) {
		String[] parts = ResourceLocation.splitObjectName(path);
		bindTexture(parts[0], parts[1]);
	}

}
