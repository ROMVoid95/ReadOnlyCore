package net.rom.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.rom.entity.IEntityTeamColorable;


/**
 * The code to rendering the entity layers.
 *
 * @author CJMinecraft
 */
public class RenderUtilities {

	/**
	 * Render.
	 *
	 * @param <T> the generic type
	 * @param renderer the renderer
	 * @param entity the entity
	 * @param overlayTexture the overlay texture
	 * @param limbSwing the limb swing
	 * @param limbSwingAmount the limb swing amount
	 * @param ageInTicks the age in ticks
	 * @param netHeadYaw the net head yaw
	 * @param headPitch the head pitch
	 * @param scale the scale
	 */
	public static <T extends EntityLiving> void render(RenderLiving<T> renderer, T entity,
			ResourceLocation overlayTexture, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale) {
		if (entity instanceof IEntityTeamColorable) {
			IEntityTeamColorable<T> entityTeam = (IEntityTeamColorable<T>) entity;
			renderer.bindTexture(overlayTexture);

			if (entity.isInvisible())
				GlStateManager.depthMask(false);
			else
				GlStateManager.depthMask(true);

			GlStateManager.color(entityTeam.getColor().getR() / 255.0F, entityTeam.getColor().getG() / 255.0F,
					entityTeam.getColor().getB() / 255.0F);
			renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
					scale);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	/**
	 * Render dynamic glow.
	 *
	 * @param <T> the generic type
	 * @param renderer the renderer
	 * @param entity the entity
	 * @param overlayTexture the overlay texture
	 * @param limbSwing the limb swing
	 * @param limbSwingAmount the limb swing amount
	 * @param ageInTicks the age in ticks
	 * @param netHeadYaw the net head yaw
	 * @param headPitch the head pitch
	 * @param scale the scale
	 * @param partialTicks the partial ticks
	 */
	public static <T extends EntityLiving> void renderDynamicGlow(RenderLiving<T> renderer, T entity,
			ResourceLocation overlayTexture, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale, float partialTicks) {
		if (entity instanceof IEntityTeamColorable) {
			IEntityTeamColorable<T> entityTeam = (IEntityTeamColorable<T>) entity;
			renderer.bindTexture(overlayTexture);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

			if (entity.isInvisible()) {
				GlStateManager.depthMask(false);
			} else {
				GlStateManager.depthMask(true);
			}

			int i = 61680;
			int j = i % 65536;
			int k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor3f(entityTeam.getColor().getR() / 255, entityTeam.getColor().getG() / 255,
					entityTeam.getColor().getB() / 255);
			renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
					scale);
			i = entity.getBrightnessForRender();
			j = i % 65536;
			k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
			renderer.setLightmap(entity);
			GlStateManager.disableBlend();
			GlStateManager.resetColor();
		}
	}

	/**
	 * Render static glow.
	 *
	 * @param <T> the generic type
	 * @param renderer the renderer
	 * @param entity the entity
	 * @param overlayTexture the overlay texture
	 * @param limbSwing the limb swing
	 * @param limbSwingAmount the limb swing amount
	 * @param ageInTicks the age in ticks
	 * @param netHeadYaw the net head yaw
	 * @param headPitch the head pitch
	 * @param scale the scale
	 * @param partialTicks the partial ticks
	 */
	public static <T extends EntityLiving> void renderStaticGlow(RenderLiving<T> renderer, T entity,
			ResourceLocation overlayTexture, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scale, float partialTicks) {
		if (entity instanceof IEntityTeamColorable) {
			if (!entity.isInvisible()) {
				renderer.bindTexture(overlayTexture);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

				int i = 61680;
				int j = i % 65536;
				int k = i / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch,
						scale);
				i = entity.getBrightnessForRender();
				j = i % 65536;
				k = i / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
				renderer.setLightmap(entity);
				GlStateManager.disableBlend();
				GlStateManager.color(1, 1, 1, 1);
			}
		}
	}
}