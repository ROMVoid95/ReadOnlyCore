package net.rom.readonlycore.base;

import net.rom.readonlycore.utils.MCUtil;
import net.rom.readonlycore.utils.ModLogger;

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
