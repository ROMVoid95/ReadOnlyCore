package net.rom.gui.menu;

import java.awt.Color;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class CustomButton extends GuiButton {
	
	private IPressable press;
	private boolean handleClick = false;
	private boolean isLeftClickDown = false;
	private boolean isUseable = true;
	
	private static int id;
	
	private Color idleColor;
	private Color hoveredColor;
	private Color idleBorderColor;
	private Color hoveredBorderColor;
	private int borderWidth = 2;
	private ResourceLocation backgroundHover;
	private ResourceLocation backgroundNormal;

	public CustomButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, IPressable onClick) {
		super(id, x, y, widthIn, heightIn, buttonText);
		this.press = onClick;
	}
	
	public CustomButton(int x, int y, int widthIn, int heightIn, String buttonText, boolean handleClick, IPressable onPress) {
		super(id, x, y, widthIn, heightIn, buttonText);
		this.handleClick = handleClick;
		this.press = onPress;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			FontRenderer font = mc.fontRenderer;
			
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			
			GlStateManager.enableBlend();
			if (this.hasColorBackground()) {
				Color border;
				if (!hovered) {
					GuiScreen.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.idleColor.getRGB());
					border = this.idleBorderColor;
				} else {
					GuiScreen.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.hoveredColor.getRGB());
					border = this.hoveredBorderColor;
				}
				if (this.hasBorder()) {
					//top
					GuiScreen.drawRect(this.x, this.y, this.x + this.width, this.y + this.borderWidth, border.getRGB());
					//bottom
					GuiScreen.drawRect(this.x, this.y + this.height - this.borderWidth, this.x + this.width, this.y + this.height, border.getRGB());
					//left
					GuiScreen.drawRect(this.x, this.y + this.borderWidth, this.x + this.borderWidth, this.y + this.height - this.borderWidth, border.getRGB());
					//right
					GuiScreen.drawRect(this.x + this.width - this.borderWidth, this.y + this.borderWidth, this.x + this.width, this.y + this.height - this.borderWidth, border.getRGB());
				}
			} else if (this.hasCustomTextureBackground()) {
				if (this.isMouseOver()) {
					Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundHover);
				} else {
					Minecraft.getMinecraft().getTextureManager().bindTexture(backgroundNormal);
				}
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				drawModalRectWithCustomSizedTexture(this.x, this.y, 0.0F, 0.0F, this.width, this.height, this.width, this.height);
			} else {
				mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
	            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	            int i = this.getHoverState(this.hovered);
	            GlStateManager.enableBlend();
	            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	            this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
	            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
			}
			
			this.mouseDragged(mc, mouseX, mouseY);
			
			int j = 14737632;
			if (packedFGColour != 0) {
				j = packedFGColour;
			} else if (!this.enabled) {
				j = 10526880;
			} else if (this.hovered) {
				j = 16777120;
			}

			this.drawCenteredString(font, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
		}

		if (this.handleClick && this.isUseable) {
			if (this.isMouseOver() && MouseInput.isLeftMouseDown() && !this.isLeftClickDown) {
				this.press.onPress(this);
				this.playPressSound(Minecraft.getMinecraft().getSoundHandler());
				this.isLeftClickDown = true;
			}
			if (!MouseInput.isLeftMouseDown()) {
				this.isLeftClickDown = false;
			}
		}
	}
	
	public void setBackgroundColor(@Nullable Color idle, @Nullable Color hovered, @Nullable Color idleBorder, @Nullable Color hoveredBorder, int borderWidth) {
		this.idleColor = idle;
		this.hoveredColor = hovered;
		this.hoveredBorderColor = hoveredBorder;
		this.idleBorderColor = idleBorder;
		
		if (borderWidth >= 0) {
			this.borderWidth = borderWidth;
		} else {
			borderWidth = 0;
		}
	}
	
	public void setBackgroundTexture(ResourceLocation normal, ResourceLocation hovered) {
		this.backgroundNormal = normal;
		this.backgroundHover = hovered;
	}
	
	public void setBackgroundTexture(ResourceLoc normal, ResourceLoc hovered) {
		if (!normal.isReady()) {
			normal.loadTexture();
		}
		if (!hovered.isReady()) {
			hovered.loadTexture();
		}
		this.backgroundHover = hovered.getResourceLocation();
		this.backgroundNormal = normal.getResourceLocation();
	}
	
	public boolean hasBorder() {
		return (this.hasColorBackground() && (this.idleBorderColor != null) && (this.hoveredBorderColor != null));
	}
	
	public boolean hasColorBackground() {
		return ((this.idleColor != null) && (this.hoveredColor != null));
	}
	
	public boolean hasCustomTextureBackground() {
		return ((this.backgroundHover != null) && (this.backgroundNormal != null));
	}
	
	@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (!this.handleClick && this.isUseable) {
			if (this.isMouseOver()) {
				this.press.onPress(this);
			}
			return super.mousePressed(mc, mouseX, mouseY);
		}
		return false;
	}
	
	public void setUseable(boolean b) {
		this.isUseable = b;
	}
	
	public boolean isUseable() {
		return this.isUseable;
	}
	
	public void setHandleClick(boolean b) {
		this.handleClick = b;
	}
	
	public interface IPressable {
		void onPress(GuiButton button);
	}

}
