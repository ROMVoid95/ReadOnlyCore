package net.rom.libs.tablet.gui;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.rom.libs.tablet.ITabletScreen;

@SuppressWarnings("unused")
public abstract class ScrollScreen implements ITabletScreen {
	private static int scroll = 0;

	private static int maxscroll = 1;

	private static ResourceLocation tabletScreenTexture = new ResourceLocation("readonlycore", "textures/tablet.png");

	private Minecraft mc = Minecraft.getMinecraft();

	public ScrollScreen() {
		scroll = 0;
	}

	public abstract String titlePage();

	public abstract ResourceLocation iconTitle();

	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY) {
		if (Mouse.hasWheel()) {
			int dWheel = Mouse.getDWheel();
			if (dWheel > 0 && scroll > 0) {
				scroll--;
			} else if (dWheel < 0 && scroll < getMaxScroll()) {
				scroll++;
			}
		}
		this.mc.getTextureManager().bindTexture(tabletScreenTexture);
		drawTexturedModalRect((x + 417), (y + 36), 17.0F, 186.0F, 492.0F, 0.0F, 14.0F, 185.0F, false, false, 512.0F,
				256.0F);
		drawTexturedModalRect((x + 417), (y + 36), 17.0F, 28.0F, 400.0F, 68.0F, 15.0F, 28.0F, false, false, 512.0F,
				256.0F);
		int pos = (getMaxScroll() > 0) ? Math.round((116 / getMaxScroll())) : getMaxScroll();
		drawTexturedModalRect((x + 420), (y + 58 + scroll * pos), 15.0F, 28.0F, 429.0F, 68.0F, 14.0F, 28.0F, false,
				false, 512.0F, 256.0F);
		drawTexturedModalRect((x + 419), (y + 194), 16.0F, 28.0F, 400.0F, 68.0F, 14.0F, 28.0F, true, true, 512.0F,
				256.0F);
	}

	public void mouseClick(int mouseX, int mouseY, int mouseButton, int x, int y) {
		if (checkClick(mouseX, mouseY, x + 420, y + 40, 10, 15) && scroll > 0)
			scroll--;
		if (checkClick(mouseX, mouseY, x + 420, y + 198, 10, 20) && scroll < getMaxScroll())
			scroll++;
	}

	public abstract String getCategory();

	public abstract int getMaxScroll();

	public int setScroll(int count) {
		return scroll = count;
	}

	public int getScroll() {
		return scroll;
	}

	private boolean checkClick(int mouseX, int mouseY, int x, int y, int sizeX, int sizeY) {
		return (mouseX >= x && mouseX <= x + sizeX && mouseY >= y && mouseY <= y + sizeY);
	}
}
