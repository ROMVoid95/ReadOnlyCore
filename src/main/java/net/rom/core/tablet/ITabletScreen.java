package net.rom.core.tablet;

import static net.rom.core.utils.MinecraftColors.Gold;
import static net.rom.core.utils.MinecraftColors.Green;
import static net.rom.core.utils.MinecraftColors.Red;
import static net.rom.core.utils.MinecraftColors.White;
import static net.rom.core.utils.MinecraftColors.Yellow;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface ITabletScreen {

	String startScreen();

	ResourceLocation startScreenIcon();

	void drawPage(int int1, int int2, FontRenderer fontRenderer, int int3, int int4);

	void mouseClick(int int1, int int2, int int3, int int4, int int5);

	String getCategory();

	default ItemStack itemIcon() {
		return ItemStack.EMPTY;
	}

	default boolean backButton() {
		return false;
	}

	default boolean rawTitle() {
		return false;
	}

	default void drawText(String text, int x, int y, int pos, FontRenderer font) {
		String[] str1 = text.split("!n");
		for (int i = pos; i < str1.length; i++) {
			String strings = str1[i];
			strings = strings.replace("!c_o", Gold(""));
			strings = strings.replace("!c_w", White(""));
			strings = strings.replace("!c_r", Red(""));
			strings = strings.replace("!c_y", Yellow(""));
			strings = strings.replace("!c_g", Green(""));
			font.drawSplitString(strings, x + 40, y + 90 + 20 * (i - pos), 360, 16777215);
		}
	}

	default void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int uWidth, int vHeight,
			boolean invertX, boolean invertY) {
		drawTexturedModalRect(x, y, width, height, u, v, uWidth, vHeight, invertX, invertY, 512.0F, 512.0F);
	}

	default void drawTexturedModalRect(float x, float y, float width, float height, float u, float v, float uWidth,
			float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY) {
		float zLevel = 0.0F;
		float texModX = 1.0F / texSizeX;
		float texModY = 1.0F / texSizeY;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		float height0 = invertY ? 0.0F : vHeight;
		float height1 = invertY ? vHeight : 0.0F;
		float width0 = invertX ? uWidth : 0.0F;
		float width1 = invertX ? 0.0F : uWidth;
		vertexbuffer.pos(x, (y + height), zLevel).tex(((u + width0) * texModX), ((v + height0) * texModY)).endVertex();
		vertexbuffer.pos((x + width), (y + height), zLevel).tex(((u + width1) * texModX), ((v + height0) * texModY))
				.endVertex();
		vertexbuffer.pos((x + width), y, zLevel).tex(((u + width1) * texModX), ((v + height1) * texModY)).endVertex();
		vertexbuffer.pos(x, y, zLevel).tex(((u + width0) * texModX), ((v + height1) * texModY)).endVertex();
		tessellator.draw();
	}
}
