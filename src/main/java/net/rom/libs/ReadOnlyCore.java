package net.rom.libs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.rom.libs.autoreg.Registrar;
import net.rom.libs.proxy.CommonProxy;

@Mod(modid = ReadOnlyCore.MODID, name = ReadOnlyCore.NAME, version = ReadOnlyCore.VERSION, acceptedMinecraftVersions = ReadOnlyCore.ACCEPTED_MC_VERSION, updateJSON = ReadOnlyCore.UPDATE_URL, dependencies = ReadOnlyCore.DEPENDENCIES)
public class ReadOnlyCore {
	public static final String MODID = "readonlycore";
	public static final String NAME = "ReadOnly Core";
	public static final String VERSION = "${version}";
	public static final String DEPENDENCIES = "";
	public static final String ACCEPTED_MC_VERSIONS = "[1.12.2]";
	public static final String ACCEPTED_MC_VERSION = ForgeVersion.mcVersion;
	public static final String SERVER_PROXY_CLASS = "net.rom.libs.proxy.ServerProxy";
	public static final String CLIENT_PROXY_CLASS = "net.rom.libs.proxy.ClientProxy";
	public static final String UPDATE_URL = "https://raw.githubusercontent.com/ROMVoid95/ReadOnlyCore/master/update.json";
	public static final Logger logger = LogManager.getFormatterLogger(NAME);

	@Mod.Instance
	public static ReadOnlyCore instance;
	
	@SidedProxy(serverSide = SERVER_PROXY_CLASS, clientSide = CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Registrar.addRegistryClasses(event.getAsmData());
		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ConfigManager.sync(MODID, Type.INSTANCE);
		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
}
