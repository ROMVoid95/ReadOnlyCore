package net.rom.core.base;

import net.rom.core.utils.MCUtil;
import net.rom.core.utils.ModLogger;

public interface IMod {
	
	String getModId();
	
	String getModName();
	
	String getVersion();
	
    int getBuildNum();

    default boolean isDevBuild() {
        return 0 == getBuildNum() || MCUtil.isDeobfuscated();
    }

    default ModLogger getLog() {
        return ModLogger.getRegisteredLogger(getModName()).orElse(new ModLogger(getModName(), getBuildNum()));
    }
}
