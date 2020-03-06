package net.rom.libs.base;

import net.rom.libs.utils.MCUtil;
import net.rom.libs.utils.ModLogger;

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
