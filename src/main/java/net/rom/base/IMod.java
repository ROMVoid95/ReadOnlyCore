package net.rom.base;

import net.rom.utils.MCUtil;
import net.rom.utils.ModLogger;


/**
 * The Interface IMod.
 */
public interface IMod {
	
	/**
	 * Gets the mod id.
	 *
	 * @return the mod id
	 */
	public String getModId();
	
	/**
	 * Gets the mod name.
	 *
	 * @return the mod name
	 */
	public String getModName();
	
	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion();
	
    /**
     * Gets the builds the num.
     *
     * @return the builds the num
     */
	public int getBuildNum();

    /**
     * Checks if is dev build.
     *
     * @return true, if is dev build
     */
    default boolean isDevBuild() {
        return 0 == getBuildNum() || MCUtil.isDeobfuscated();
    }

    /**
     * Gets the log.
     *
     * @return the log
     */
    default ModLogger getLog() {
        return ModLogger.getRegisteredLogger(getModName()).orElse(new ModLogger(getModName(), getBuildNum()));
    }
}
