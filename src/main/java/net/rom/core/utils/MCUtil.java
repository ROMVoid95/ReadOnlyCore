package net.rom.core.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public final class MCUtil {
	
	private MCUtil() {
		throw new IllegalAccessError("Util Class");
	}
	
    /**
     * Check if this is the client side.
     *
     * @return True if and only if we are on the client side
     */
	public static boolean isClient() {
		return FMLCommonHandler.instance().getSide().isClient();
	}
	
    /**
     * Check if this is the server side.
     *
     * @return True if and only if we are on the server side
     */
    public static boolean isServer() {
        return FMLCommonHandler.instance().getSide().isServer();
    }

    /**
     * Check if this is a deobfuscated (development) environment.
     *
     * @return True if and only if we are running in a deobfuscated environment
     */
    public static boolean isDeobfuscated() {	
        return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }
    
	public static String[] splitToNamespaceAndPath(String fullPath, char splitter) {
		String[] s = new String[] { "minecraft", fullPath };
		int i = fullPath.indexOf(splitter);
		if (i >= 0) {
			s[1] = fullPath.substring(i + 1, fullPath.length());
			if (i >= 1) {
				s[0] = fullPath.substring(0, i);
			}
		}
		return s;
	}
	
	public static BufferedImage getImageResourceAsStream(ResourceLocation r) {
		try {
			return TextureUtil.readBufferedImage(Minecraft.getMinecraft().getResourceManager().getResource(r).getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
