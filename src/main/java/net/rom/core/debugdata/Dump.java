package net.rom.core.debugdata;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.rom.core.ReadOnlyCore;

public final class Dump {
	private static final String REGISTRY_NAME_IS_NULL = "Registry name is null! This indicates a broken mod and is a serious problem!";
	private static final String SEPARATOR = "--------------------------------------------------------------------------------";

	private Dump() {
		throw new IllegalAccessError("Utility class");
	}

	public static void dumpBlocks() {
		ReadOnlyCore.LOGGER.info(SEPARATOR);
		List<String> lines = new ArrayList<>();

		for (Block block : ForgeRegistries.BLOCKS) {
			try {
				ResourceLocation name = Objects.requireNonNull(block.getRegistryName(), REGISTRY_NAME_IS_NULL);
				String translatedName = I18n.format(block.getTranslationKey() + ".name");
				lines.add(String.format("%-60s %-60s", name, translatedName));
			} catch (Exception ex) {
				ReadOnlyCore.LOGGER.warn("*** Error on block: {} ***", block);
				ReadOnlyCore.LOGGER.catching(ex);
			}
		}

		lines.sort(String::compareToIgnoreCase);
		lines.forEach(ReadOnlyCore.LOGGER::info);
		ReadOnlyCore.LOGGER.info(SEPARATOR);
	}

	public static void dumpEnchantments() {
		ReadOnlyCore.LOGGER.info(SEPARATOR);
		for (Enchantment ench : Enchantment.REGISTRY) {
			try {
				ResourceLocation name = Objects.requireNonNull(ench.getRegistryName(), REGISTRY_NAME_IS_NULL);
				String translatedName = ench.getTranslatedName(1).replaceFirst(" I$", "");
				String type = ench.type == null ? "null" : ench.type.name();
				ReadOnlyCore.LOGGER.info(String.format("%-30s %-40s type=%-10s", translatedName, name, type));
			} catch (Exception ex) {
				ReadOnlyCore.LOGGER.warn("*** Error on enchantment: {} ***", ench);
				ReadOnlyCore.LOGGER.catching(ex);
			}
		}
		ReadOnlyCore.LOGGER.info(SEPARATOR);
	}

	public static void dumpEntityList() {
		ReadOnlyCore.LOGGER.info(SEPARATOR);
		List<String> list = Lists.newArrayList();

		for (EntityEntry entry : ForgeRegistries.ENTITIES) {
			try {
				ResourceLocation name = Objects.requireNonNull(entry.getRegistryName(), REGISTRY_NAME_IS_NULL);
				Class<? extends Entity> clazz = EntityList.getClass(entry.getRegistryName());
				String oldName = EntityList.getTranslationName(name);
				int id = EntityList.getID(clazz);
				list.add(String.format("%-30s %4d   %-40s %-40s", oldName, id, name, clazz));
			} catch (Exception ex) {
				ReadOnlyCore.LOGGER.warn("*** Error on entity: {} ***", entry.getEntityClass());
				ReadOnlyCore.LOGGER.catching(ex);
			}
		}

		list.sort(String::compareToIgnoreCase);
		list.forEach(ReadOnlyCore.LOGGER::info);
		ReadOnlyCore.LOGGER.info(SEPARATOR);
	}

	public static void dumpItems() {
		ReadOnlyCore.LOGGER.info(SEPARATOR);
		List<String> lines = new ArrayList<>();

		for (Item item : ForgeRegistries.ITEMS) {
			try {
				ResourceLocation name = Objects.requireNonNull(item.getRegistryName(), REGISTRY_NAME_IS_NULL);
				String translatedName = I18n.format(item.getTranslationKey() + ".name");
				lines.add(String.format("%-60s %-60s", name, translatedName));
			} catch (Exception ex) {
				ReadOnlyCore.LOGGER.warn("*** Error on item: {} ***", item);
				ReadOnlyCore.LOGGER.catching(ex);
			}
		}

//        lines.sort(String::compareToIgnoreCase);
		lines.forEach(ReadOnlyCore.LOGGER::info);
		ReadOnlyCore.LOGGER.info(SEPARATOR);
	}

	public static void dumpPotionEffects() {
		ReadOnlyCore.LOGGER.info(SEPARATOR);
		for (Potion pot : Potion.REGISTRY) {
			try {
				ResourceLocation name = Objects.requireNonNull(pot.getRegistryName(), REGISTRY_NAME_IS_NULL);
				ReadOnlyCore.LOGGER.info(String.format("%-30s %-40s", pot.getName(), name));
			} catch (Exception ex) {
				ReadOnlyCore.LOGGER.warn("*** Error on potion: {} ***", pot);
				ReadOnlyCore.LOGGER.catching(ex);
			}
		}
		ReadOnlyCore.LOGGER.info(SEPARATOR);
	}

	public static void dumpRecipes() {
		ReadOnlyCore.LOGGER.info(SEPARATOR);
		ReadOnlyCore.LOGGER.info("The following is a list of all recipes registered as of Silent Lib's post-init:");

		for (IRecipe rec : CraftingManager.REGISTRY) {
			try {
				ResourceLocation name = Objects.requireNonNull(rec.getRegistryName(), REGISTRY_NAME_IS_NULL);
				int id = CraftingManager.REGISTRY.getIDForObject(rec);
				if (id < 0)
					throw new IndexOutOfBoundsException("id < 0");
				ReadOnlyCore.LOGGER.info(String.format("%-6d %-40s", id, name));
			} catch (Exception ex) {
				ReadOnlyCore.LOGGER.info("*** Error on recipe: {} ***", rec);
				throw ex;
			}
		}

		ReadOnlyCore.LOGGER.info(SEPARATOR);
	}
}
