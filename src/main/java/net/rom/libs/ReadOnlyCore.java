package net.rom.libs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.rom.libs.base.IMod;
import net.rom.libs.network.NHandler;
import net.rom.libs.proxy.LibCommonProxy;

@Mod(modid = ReadOnlyCore.MODID, name = ReadOnlyCore.NAME, version = ReadOnlyCore.VERSION, acceptedMinecraftVersions = ReadOnlyCore.ACCEPTED_MC_VERSION, updateJSON = ReadOnlyCore.UPDATE_URL, dependencies = ReadOnlyCore.DEPENDENCIES)
public class ReadOnlyCore implements IMod{
	public static final String MODID = "readonlycore";
	public static final String NAME = "ReadOnly Core";
	public static final String VERSION = "${version}";
	public static final int BUILD_NUM = 0;
	public static final String DEPENDENCIES = "";
	public static final String ACCEPTED_MC_VERSIONS = "[1.12.2]";
	public static final String ACCEPTED_MC_VERSION = ForgeVersion.mcVersion;
	public static final String SERVER_PROXY_CLASS = "net.rom.libs.proxy.LibServerProxy";
	public static final String CLIENT_PROXY_CLASS = "net.rom.libs.proxy.LibClientProxy";
	public static final String UPDATE_URL = "https://raw.githubusercontent.com/ROMVoid95/ReadOnlyCore/master/update.json";

	public static NHandler network;
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	@Instance(MODID)
	public static ReadOnlyCore instance;
	
	@SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
	public static LibCommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network = new NHandler(MODID);
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ConfigManager.sync(MODID, Type.INSTANCE);
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@Override
	public String getModId() {
		return MODID;
	}

	@Override
	public String getModName() {
		return NAME;
	}

	@Override
	public String getVersion() {
		return VERSION;
	}

	@Override
	public int getBuildNum() {
		return BUILD_NUM;
	}
}
