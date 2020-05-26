package net.rom.core.gui.menu;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CleaningDynamicTexture extends DynamicTexture {

	public CleaningDynamicTexture(BufferedImage bufferedImage) {
		super(bufferedImage);
	}
	
	public void updateDynamicTexture() {
		super.updateDynamicTexture();
		clearTextureData(this);
	}
	
	private static void clearTextureData(DynamicTexture texture) {
		try {
			Field f =  ObfuscationReflectionHelper.findField(DynamicTexture.class, "field_110566_b"); 
			f.set(texture, new int[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
