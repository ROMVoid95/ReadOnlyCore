package net.rom.readonlycore.client.render;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.rom.readonlycore.utils.CoreUtil;

public class Texture extends ResourceLocation {
	public Texture(ResourceLocation resource) {
		this(resource.getNamespace(), resource.getPath());
	}

	public Texture(String location) {
		super(location);
	}

	public Texture(String domain, String location) {
		super(domain, location);
	}

	@SideOnly(Side.CLIENT)
	public void bind() {
		CoreUtil.bindTexture(this);
	}
}