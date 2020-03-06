package net.rom.libs.tablet.gui;

import static net.rom.libs.utils.MinecraftColors.Gold;

import java.io.IOException;
import java.util.Map;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.rom.libs.tablet.ITabletScreen;
import net.rom.libs.utils.TabletHelper;
import net.rom.libs.utils.TextUtil;

@SuppressWarnings("unused")
public class ReadOnlyTablet extends GuiScreen {

	private final int tabletImgHeight = 250;

	private final int tabletImgWidth = 450;

	private static ResourceLocation tabletScreenTexture = new ResourceLocation("readonlycore", "textures/tablet.png");

	private int scroll = 0;

	private static TextUtil txt = new TextUtil();

	public enum Mode {
		CATEGORIES, PAGES, TEXT;
	}

	private Mode mode = Mode.CATEGORIES;

	private String category = TabletHelper.Tablet_Catagories.GENERAL.getName();

	private static ITabletScreen page;

	private int maxX = 0;

	public ReadOnlyTablet() {
	}

	public ReadOnlyTablet(Mode mode, String category, ITabletScreen page) {
		this.mode = mode;
		this.category = category;
		ReadOnlyTablet.page = page;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(tabletScreenTexture);
		int offsetFromScreenLeft = (this.width - 450) / 2;
		int offsetFromScreenTop = (this.height - 250) / 2;
		drawTexturedModalRect(offsetFromScreenLeft, offsetFromScreenTop, 450.0F, 256.0F, 0.0F, 0.0F, 400.0F, 256.0F,
				false, false, 512.0F, 256.0F);
		int posY = 0;
		if (mouseX >= offsetFromScreenLeft + 25 && mouseX < offsetFromScreenLeft + 58
				&& mouseY >= offsetFromScreenTop + 18 && mouseY <= offsetFromScreenTop + 32)
			posY = 17;
		if (this.mode != Mode.CATEGORIES) {
			drawTexturedModalRect((offsetFromScreenLeft + 25), (offsetFromScreenTop + 18), 30.0F, 15.0F, 400.0F, posY,
					20.0F, 15.0F, false, false, 512.0F, 256.0F);
			drawTexturedModalRect((offsetFromScreenLeft + 40), (offsetFromScreenTop + 18), 30.0F, 15.0F, 467.0F, posY,
					20.0F, 15.0F, false, false, 512.0F, 256.0F);
			this.fontRenderer.drawString(txt.translation("tablet.button.back.name").getUnformattedComponentText(),
					offsetFromScreenLeft + 39, offsetFromScreenTop + 22, 16777215);
		}
		if (this.mode == Mode.CATEGORIES) {
			int i = 0, offsetX = 65, offsetY = 0;
			for (Map.Entry<String, ResourceLocation> entry : (Iterable<Map.Entry<String, ResourceLocation>>) TabletHelper.TABLET_CATAGORIES
					.entrySet()) {
				offsetY = i / 6 * 60;
				int k = (i > i % 6) ? (i - 6 * i / 6) : i;
				offsetX = 65 * k;
				this.mc.getTextureManager().bindTexture(tabletScreenTexture);
				drawTexturedModalRect((offsetFromScreenLeft + 42 + offsetX), (offsetFromScreenTop + 52 + offsetY),
						36.0F, 35.0F, 460.0F, 34.0F, 22.0F, 24.0F, false, false, 512.0F, 256.0F);
				this.mc.getTextureManager().bindTexture(entry.getValue());
				drawTexturedModalRect((offsetFromScreenLeft + 51 + offsetX), (offsetFromScreenTop + 60 + offsetY),
						16.0F, 16.0F, 0.0F, 0.0F, 16.0F, 16.0F, false, false, 16.0F, 16.0F);
				this.fontRenderer.drawSplitString(
						txt.translation("tablet.category." + (String) entry.getKey() + ".name")
								.getUnformattedComponentText(),
						offsetFromScreenLeft + offsetX + 60 - this.fontRenderer.getStringWidth(entry.getKey()) / 2,
						offsetFromScreenTop + 85 + offsetY, 50, 16777215);
				i++;
			}
		} else if (this.mode == Mode.PAGES) {
			this.mc.getTextureManager().bindTexture(tabletScreenTexture);
			this.fontRenderer.drawString(
					txt.translation("tablet.category." + this.category + ".name").getUnformattedComponentText(),
					this.width / 2 - this.fontRenderer.getStringWidth(this.category) / 2, offsetFromScreenTop + 20,
					16777215);
			int getMaxScroll = 0;
			this.maxX = 0;
			for (ITabletScreen page : TabletHelper.TABLET_PAGES) {
				if (page.getCategory().equals(this.category)) {
					int currX = this.fontRenderer.getStringWidth(
							page.rawTitle() ? txt.translation(page.startScreen()).getUnformattedComponentText()
									: txt.translation("tablet.page." + page.startScreen() + ".name")
											.getUnformattedComponentText());
					this.maxX = (this.maxX < currX) ? currX : this.maxX;
					getMaxScroll++;
				}
			}
			getMaxScroll -= 7;
			if (getMaxScroll > 8) {
				this.mc.getTextureManager().bindTexture(tabletScreenTexture);
				drawTexturedModalRect((offsetFromScreenLeft + 417), (offsetFromScreenTop + 36), 17.0F, 186.0F, 492.0F,
						0.0F, 14.0F, 185.0F, false, false, 512.0F, 256.0F);
				drawTexturedModalRect((offsetFromScreenLeft + 417), (offsetFromScreenTop + 36), 17.0F, 28.0F, 400.0F,
						68.0F, 15.0F, 28.0F, false, false, 512.0F, 256.0F);
				drawTexturedModalRect((offsetFromScreenLeft + 419), (offsetFromScreenTop + 194), 16.0F, 28.0F, 400.0F,
						68.0F, 14.0F, 28.0F, true, true, 512.0F, 256.0F);
				int pos = (getMaxScroll > 0) ? Math.round((118 / getMaxScroll)) : getMaxScroll;
				drawTexturedModalRect((offsetFromScreenLeft + 420), (offsetFromScreenTop + 60 + this.scroll * pos),
						15.0F, 28.0F, 429.0F, 68.0F, 14.0F, 28.0F, false, false, 512.0F, 256.0F);
			}
			if (Mouse.hasWheel()) {
				int dWheel = Mouse.getDWheel();
				if (dWheel > 0 && this.scroll > 0) {
					this.scroll--;
				} else if (dWheel < 0 && this.scroll < getMaxScroll) {
					this.scroll++;
				}
			}
			int i = 0, offsetX = 21;
			for (ITabletScreen page : TabletHelper.TABLET_PAGES) {
				if (page.getCategory().equals(this.category)) {
					if (i < this.scroll || i > 7 + this.scroll) {
						i++;
						continue;
					}
					this.mc.getTextureManager().bindTexture(tabletScreenTexture);
					posY = 0;
					if (mouseX >= offsetFromScreenLeft + 44 && mouseX < offsetFromScreenLeft + 40 + 25 + this.maxX
							&& mouseY >= offsetFromScreenTop + 41 + offsetX * (i - this.scroll)
							&& mouseY <= offsetFromScreenTop + 41 + offsetX * (i - this.scroll) + 16)
						posY = 17;
					drawTexturedModalRect((offsetFromScreenLeft + 40),
							(offsetFromScreenTop + 41 + offsetX * (i - this.scroll)), 25.0F, 16.0F, 400.0F, posY, 20.0F,
							15.0F, false, false, 512.0F, 256.0F);
					drawTexturedModalRect((offsetFromScreenLeft + 40 + 16),
							(offsetFromScreenTop + 41 + offsetX * (i - this.scroll)), this.maxX, 16.0F, 410.0F, posY,
							70.0F, 15.0F, false, false, 512.0F, 256.0F);
					drawTexturedModalRect((offsetFromScreenLeft + 40 + this.maxX),
							(offsetFromScreenTop + 41 + offsetX * (i - this.scroll)), 25.0F, 16.0F, 467.0F, posY, 20.0F,
							15.0F, false, false, 512.0F, 256.0F);
					if (page.startScreenIcon() != null) {
						this.mc.getTextureManager().bindTexture(tabletScreenTexture);
						drawTexturedModalRect((offsetFromScreenLeft + 20),
								(offsetFromScreenTop + 41 + offsetX * (i - this.scroll)), 21.0F, 21.0F, 461.0F, 35.0F,
								18.0F, 21.0F, false, false, 512.0F, 256.0F);
						this.mc.getTextureManager().bindTexture(page.startScreenIcon());
						drawTexturedModalRect((offsetFromScreenLeft + 25),
								(offsetFromScreenTop + 45 + offsetX * (i - this.scroll)), 12.0F, 12.0F, 0.0F, 0.0F,
								16.0F, 16.0F, false, false, 16.0F, 16.0F);
					}
					if (page.itemIcon() != ItemStack.EMPTY) {
						this.mc.getTextureManager().bindTexture(tabletScreenTexture);
						drawTexturedModalRect((offsetFromScreenLeft + 20),
								(offsetFromScreenTop + 41 + offsetX * (i - this.scroll)), 21.0F, 21.0F, 461.0F, 35.0F,
								18.0F, 21.0F, false, false, 512.0F, 256.0F);
						RenderHelper.enableGUIStandardItemLighting();
						this.mc.getRenderItem().renderItemAndEffectIntoGUI(page.itemIcon(), offsetFromScreenLeft + 23,
								offsetFromScreenTop + 43 + offsetX * (i - this.scroll));
						RenderHelper.disableStandardItemLighting();
					}
					this.fontRenderer.drawString(
							page.rawTitle() ? txt.translation(page.startScreen()).getUnformattedComponentText()
									: txt.translation("tablet.page." + page.startScreen() + ".name")
											.getUnformattedComponentText(),
							offsetFromScreenLeft + 52, offsetFromScreenTop + 45 + offsetX * (i - this.scroll),
							16777215);
					i++;
				}
			}
			if (i == 0)
				this.fontRenderer.drawString("Empty! this will be fixed in later versions!", offsetFromScreenLeft + 52,
						offsetFromScreenTop + 45 + offsetX * i, 16777215);
		} else if (this.mode == Mode.TEXT) {
			String s = txt.translation("tablet.page." + ReadOnlyTablet.page.startScreen() + ".name")
					.getUnformattedComponentText();
			if (ReadOnlyTablet.page.rawTitle())
				s = ReadOnlyTablet.page.startScreen();
			this.fontRenderer.drawString(s, this.width / 2 - this.fontRenderer.getStringWidth(this.category) - 10,
					offsetFromScreenTop + 20, 16777215);
			ReadOnlyTablet.page.drawPage(offsetFromScreenLeft, offsetFromScreenTop, this.fontRenderer, mouseX, mouseY);
		}
		this.fontRenderer.drawString(Gold("tablet in Beta"), offsetFromScreenLeft + 370, offsetFromScreenTop + 22,
				16777215);
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int offsetFromScreenLeft = (this.width - 450) / 2;
		int offsetFromScreenTop = (this.height - 250) / 2;
		if (this.mode == Mode.CATEGORIES) {
			int i = 0, offsetX = 65, offsetY = 0;
			for (Map.Entry<String, ResourceLocation> entry : (Iterable<Map.Entry<String, ResourceLocation>>) TabletHelper.TABLET_CATAGORIES
					.entrySet()) {
				offsetY = i / 6 * 60;
				int k = (i > i % 6) ? (i - 6 * i / 6) : i;
				offsetX = 65 * k;
				if (mouseX >= offsetFromScreenLeft + 30 + offsetX && mouseX <= offsetFromScreenLeft + 70 + offsetX + 10
						&& mouseY >= offsetFromScreenTop + 50 + offsetY
						&& mouseY <= offsetFromScreenTop + 100 + offsetY) {
					this.category = entry.getKey();
					this.mode = Mode.PAGES;
				}
				i++;
			}
		} else if (this.mode == Mode.PAGES) {
			int i = 0, offsetX = 21;
			for (ITabletScreen entry : TabletHelper.TABLET_PAGES) {
				if (entry.getCategory().equals(this.category)) {
					if (i < this.scroll || i > 7 + this.scroll) {
						i++;
						continue;
					}
					if (mouseX >= offsetFromScreenLeft + 44 && mouseX < offsetFromScreenLeft + 40 + 25 + this.maxX
							&& mouseY >= offsetFromScreenTop + 40 + offsetX * (i - this.scroll)
							&& mouseY <= offsetFromScreenTop + 56 + offsetX * (i - this.scroll)) {
						try {
							page = (ITabletScreen) entry.getClass().newInstance();
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
						this.mode = Mode.TEXT;
					}
					i++;
				}
			}
			if (mouseX >= offsetFromScreenLeft + 25 && mouseX < offsetFromScreenLeft + 58
					&& mouseY >= offsetFromScreenTop + 18 && mouseY <= offsetFromScreenTop + 32) {
				this.mode = Mode.CATEGORIES;
				this.scroll = 0;
			}
		} else if (this.mode == Mode.TEXT) {
			page.mouseClick(mouseX, mouseY, mouseButton, offsetFromScreenLeft, offsetFromScreenTop);
			if (mouseX >= offsetFromScreenLeft + 25 && mouseX < offsetFromScreenLeft + 58
					&& mouseY >= offsetFromScreenTop + 18 && mouseY <= offsetFromScreenTop + 32)
				if (!page.backButton())
					this.mode = Mode.PAGES;
		}
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	public void drawTexturedModalRect(int x, int y, int width, int height, int u, int v, int uWidth, int vHeight,
			boolean invertX, boolean invertY) {
		drawTexturedModalRect(x, y, width, height, u, v, uWidth, vHeight, invertX, invertY, 512.0F, 512.0F);
	}

	public void drawTexturedModalRect(float x, float y, float width, float height, float u, float v, float uWidth,
			float vHeight, boolean invertX, boolean invertY, float texSizeX, float texSizeY) {
		float texModX = 1.0F / texSizeX;
		float texModY = 1.0F / texSizeY;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		float height0 = invertY ? 0.0F : vHeight;
		float height1 = invertY ? vHeight : 0.0F;
		float width0 = invertX ? uWidth : 0.0F;
		float width1 = invertX ? 0.0F : uWidth;
		vertexbuffer.pos(x, (y + height), this.zLevel).tex(((u + width0) * texModX), ((v + height0) * texModY))
				.endVertex();
		vertexbuffer.pos((x + width), (y + height), this.zLevel)
				.tex(((u + width1) * texModX), ((v + height0) * texModY)).endVertex();
		vertexbuffer.pos((x + width), y, this.zLevel).tex(((u + width1) * texModX), ((v + height1) * texModY))
				.endVertex();
		vertexbuffer.pos(x, y, this.zLevel).tex(((u + width0) * texModX), ((v + height1) * texModY)).endVertex();
		tessellator.draw();
	}
}
