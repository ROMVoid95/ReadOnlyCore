package net.rom.libs.tablet.gui;

import org.lwjgl.opengl.GL11;

import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public abstract class TabletScreenCrafting extends ScrollScreen {

	public enum Recipe_Type {
		CRAFTING_TABLE(new ItemStack(Blocks.CRAFTING_TABLE)), NASA_WORKBENCH(new ItemStack(GCBlocks.nasaWorkbench));

		private ItemStack item;

		Recipe_Type(ItemStack item) {
			this.item = item;
		}

		public ItemStack getItem() {
			return this.item;
		}
	}

	protected Minecraft mc = Minecraft.getMinecraft();

	protected static final ResourceLocation tabletScreenTexture = new ResourceLocation("readonlycore",
			"textures/tablet.png");

	public abstract String titlePage();

	public abstract ResourceLocation iconTitle();

	public abstract int getMaxScroll();

	public abstract String getCategory();

	public abstract ItemStack getItem();

	public ItemStack itemIcon() {
		return getItem();
	}

	public ItemStack[] getRecipe() {
		IRecipe rec = CraftingManager.getRecipe((ResourceLocation) Item.REGISTRY.getNameForObject(getItem().getItem()));
		ItemStack[] items = new ItemStack[9];
		if (rec != null) {
			int i = 0;
			for (Ingredient s : rec.getIngredients()) {
				items[i] = s.getMatchingStacks()[0];
				i++;
			}
		}
		return items;
	}

	public abstract Recipe_Type getRecipeType();

	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY) {
		if (getScroll() > 0)
			super.drawPage(x, y, font, mouseX, mouseY);
		RenderHelper.enableGUIStandardItemLighting();
		this.mc.getRenderItem().renderItemAndEffectIntoGUI(getRecipeType().getItem(), x + 265, y + 47);
		RenderHelper.disableStandardItemLighting();
		drawText("Recipe for: " + getRecipeType().getItem().getDisplayName(), x + 248, y - 40, 0, font);
		if ((getRecipeType() == Recipe_Type.CRAFTING_TABLE && (getRecipe()).length > 0)) {
			drawText("Components: ", x + 275, y - 25, 0, font);
			this.mc.renderEngine.bindTexture(tabletScreenTexture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			for (int i = 0; i < 3; i++) {
				for (int k = 0; k < 3; k++) {
					if (checkClick(mouseX, mouseY, x + 310 + k * 25, y + 82 + i * 25, 16, 16)) {
						drawTexturedModalRect((x + 308 + k * 25), (y + 80 + i * 25), 20.0F, 20.0F, 473.0F, 94.0F, 20.0F,
								20.0F, false, false, 576.0F, 250.0F);
					} else {
						drawTexturedModalRect((x + 308 + k * 25), (y + 80 + i * 25), 20.0F, 20.0F, 452.0F, 94.0F, 20.0F,
								20.0F, false, false, 576.0F, 250.0F);
					}
				}
			}
			int z = 0, j = 0;
			for (ItemStack s : getRecipe()) {
				RenderHelper.enableGUIStandardItemLighting();
				this.mc.getRenderItem().renderItemAndEffectIntoGUI(s, x + 310 + j * 25, y + 82 + z * 25);
				RenderHelper.disableStandardItemLighting();
				if (checkClick(mouseX, mouseY, x + 310 + j * 25, y + 82 + z * 25, 16, 16))
					drawToolTip(x + 308 + j * 25, y + 80 + z * 25, s.getDisplayName());
				j++;
				if (j % 3 == 0) {
					z++;
					j = 0;
				}
			}
			this.mc.getTextureManager().bindTexture(tabletScreenTexture);
			drawTexturedModalRect((x + 257), (y + 36), 4.0F, 188.0F, 492.0F, 10.0F, 14.0F, 160.0F, false, false, 512.0F,
					256.0F);
		}
		String str = GCCoreUtil.translate("tablet.page." + titlePage() + ".text");
		drawText(str, x, y - 40, getScroll(), font);
	}

	protected boolean checkClick(int mouseX, int mouseY, int x, int y, int sizeX, int sizeY) {
		return (mouseX >= x && mouseX <= x + sizeX && mouseY >= y && mouseY <= y + sizeY);
	}

	protected void drawToolTip(int mousePosX, int mousePosY, String tooltip) {
		drawToolTip(mousePosX, mousePosY, tooltip, this.mc.fontRenderer.getStringWidth(tooltip), 8);
	}

	protected void drawToolTip(int mousePosX, int mousePosY, String tooltip, int width, int height) {
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, 300.0F);
		int k = width;
		int j2 = mousePosX - k / 2;
		int k2 = mousePosY - 12;
		int i1 = height;
		int j1 = ColorUtil.to32BitColor(190, 0, 153, 255);
		drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
		drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
		drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
		drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
		drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
		int k1 = ColorUtil.to32BitColor(170, 0, 153, 255);
		int l1 = (k1 & 0xFEFEFE) >> 1 | k1 & 0xFF000000;
		drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
		drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
		drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
		drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);
		this.mc.fontRenderer.drawSplitString(tooltip, j2, k2, 150, ColorUtil.to32BitColor(255, 255, 255, 255));
		GL11.glPopMatrix();
	}

	protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
		float f = (startColor >> 24 & 0xFF) / 255.0F;
		float f1 = (startColor >> 16 & 0xFF) / 255.0F;
		float f2 = (startColor >> 8 & 0xFF) / 255.0F;
		float f3 = (startColor & 0xFF) / 255.0F;
		float f4 = (endColor >> 24 & 0xFF) / 255.0F;
		float f5 = (endColor >> 16 & 0xFF) / 255.0F;
		float f6 = (endColor >> 8 & 0xFF) / 255.0F;
		float f7 = (endColor & 0xFF) / 255.0F;
		float zLevel = 0.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(right, top, zLevel).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos(left, top, zLevel).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos(left, bottom, zLevel).color(f5, f6, f7, f4).endVertex();
		bufferbuilder.pos(right, bottom, zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}
}
