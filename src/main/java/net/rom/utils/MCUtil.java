package net.rom.utils;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.FMLCommonHandler;


/**
 * The Class MCUtil.
 */
public final class MCUtil {

	/**
	 * Instantiates a new MC util.
	 */
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

	/**
	 * Calculate celestial angle.
	 *
	 * @param worldTime the world time
	 * @param renderPartialTicks the render partial ticks
	 * @return the float
	 */
	public static float calculateCelestialAngle(long worldTime, float renderPartialTicks) {
		long cycleTime = 48000L;
		int remainingTime = (int) (worldTime % cycleTime);
		float angle = (remainingTime + renderPartialTicks) / cycleTime - 0.25F;

		if (angle < 0.0F) {
			angle += 1.0F;
		}

		if (angle > 1.0F) {
			angle -= 1.0F;
		}

		float anglePrev = angle;
		angle = 1.0F - (float) ((Math.cos(angle * Math.PI) + 1.0D) / 2.0D);
		angle = anglePrev + (angle - anglePrev) / 3.0F;

		return angle;
	}

}
