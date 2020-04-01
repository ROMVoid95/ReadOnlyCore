package net.rom.api.util;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.rom.api.core.ICanSecure;
import net.rom.api.core.ICanSecure.AccessState;

public class SecurityUtil {

	public static final GameProfile UNKNOWN_GAME_PROFILE = new GameProfile(
			UUID.fromString("1ef1a6f0-87bc-4e78-0a0b-c6824eb787ea"), "[None]");

	private SecurityUtil() {

	}

	public static boolean isDefaultUUID(UUID uuid) {

		return uuid == null || (uuid.version() == 4 && uuid.variant() == 0);
	}

	public static UUID getID(EntityPlayer player) {

		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		if (server != null && server.isServerRunning()) {
			return player.getGameProfile().getId();
		}
		return getClientId(player);
	}

	private static UUID cachedId;

	private static UUID getClientId(EntityPlayer player) {

		if (player != Minecraft.getMinecraft().player) {
			return player.getGameProfile().getId();
		}
		if (cachedId == null) {
			cachedId = Minecraft.getMinecraft().player.getGameProfile().getId();
		}
		return cachedId;
	}

	/* NBT TAG HELPER */
	public static NBTTagCompound setItemStackTagSecure(NBTTagCompound tag, ICanSecure tile) {

		if (tile == null) {
			return null;
		}
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setBoolean("Secure", true);
		tag.setByte("Access", (byte) tile.getAccess().ordinal());
		tag.setString("OwnerUUID", tile.getOwner().getId().toString());
		tag.setString("Owner", tile.getOwner().getName());
		return tag;
	}

	/**
	 * Adds Security information to ItemStacks.
	 */
	public static void addOwnerInformation(ItemStack stack, List<String> list) {

		if (SecurityUtil.isSecure(stack)) {
			boolean hasUUID = stack.getTagCompound().hasKey("OwnerUUID");
			if (!stack.getTagCompound().hasKey("Owner") && !hasUUID) {
				list.add(StringUtil.localize("info.readonly.owner") + ": " + StringUtil.localize("info.readonly.none"));
			} else {
				if (hasUUID && stack.getTagCompound().hasKey("Owner")) {
					list.add(StringUtil.localize("info.readonly.owner") + ": "
							+ stack.getTagCompound().getString("Owner") + " \u0378");
				} else {
					list.add(StringUtil.localize("info.readonly.owner") + ": "
							+ StringUtil.localize("info.readonly.anotherplayer"));
				}
			}
		}
	}

	public static void addAccessInformation(ItemStack stack, List<String> list) {

		if (SecurityUtil.isSecure(stack)) {
			String accessString = "";
			switch (ICanSecure.AccessState.values()[stack.getTagCompound().getByte("Access")]) {
			case OPEN:
				accessString = StringUtil.localize("info.readonly.open");
				break;
			case TRUSTED:
				accessString = StringUtil.localize("info.readonly.trusted");
				break;
			case CLAN:
				accessString = StringUtil.localize("info.readonly.clan");
				break;
			case PRIVATE:
				accessString = StringUtil.localize("info.readonly.private");
				break;
			case LOCKDOWN:
				accessString = StringUtil.localize("info.readonly.lockdown");
				break;
			}
			list.add(StringUtil.localize("info.readonly.access") + ": " + accessString);
		}
	}

	/* ITEM HELPERS */
	public static boolean isSecure(ItemStack stack) {

		return stack.hasTagCompound() && stack.getTagCompound().hasKey("Secure");
	}

	public static ItemStack setSecure(ItemStack stack) {

		if (isSecure(stack)) {
			return stack;
		}
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setBoolean("Secure", true);
		stack.getTagCompound().setByte("Access", (byte) 0);
		return stack;
	}

	public static ItemStack removeSecure(ItemStack stack) {

		if (!isSecure(stack)) {
			return stack;
		}
		stack.getTagCompound().removeTag("Secure");
		stack.getTagCompound().removeTag("Access");
		stack.getTagCompound().removeTag("OwnerUUID");
		stack.getTagCompound().removeTag("Owner");

		if (stack.getTagCompound().hasNoTags()) {
			stack.setTagCompound(null);
		}
		return stack;
	}

	public static boolean setAccess(ItemStack stack, AccessState access) {

		if (!isSecure(stack)) {
			return false;
		}
		stack.getTagCompound().setByte("Access", (byte) access.ordinal());
		return true;
	}

	public static AccessState getAccess(ItemStack stack) {

		return !stack.hasTagCompound() ? AccessState.OPEN
				: AccessState.values()[stack.getTagCompound().getByte("Access")];
	}

	public static boolean setOwner(ItemStack stack, GameProfile name) {

		if (!isSecure(stack)) {
			return false;
		}
		stack.setTagInfo("OwnerUUID", new NBTTagString(name.getId().toString()));
		stack.setTagInfo("Owner", new NBTTagString(name.getName()));
		return true;
	}

	public static GameProfile getOwner(ItemStack stack) {

		if (stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();

			String uuid = nbt.getString("OwnerUUID");
			String name = nbt.getString("Owner");
			if (!Strings.isNullOrEmpty(uuid)) {
				return new GameProfile(UUID.fromString(uuid), name);
			} else if (!Strings.isNullOrEmpty(name)) {
				return new GameProfile(UUID.fromString(PreYggdrasilConverter
						.convertMobOwnerIfNeeded(FMLCommonHandler.instance().getMinecraftServerInstance(), name)),
						name);
			}
		}
		return UNKNOWN_GAME_PROFILE;
	}

	public static GameProfile getProfile(UUID uuid, String name) {

		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		GameProfile owner = server.getPlayerProfileCache().getProfileByUUID(uuid);
		if (owner == null) {
			GameProfile temp = new GameProfile(uuid, name);
			owner = server.getMinecraftSessionService().fillProfileProperties(temp, true);
			if (owner != temp) {
				server.getPlayerProfileCache().addEntry(owner);
			}
		}
		return owner;
	}

	public static String getOwnerName(ItemStack stack) {

		NBTTagCompound nbt = stack.getTagCompound();
		boolean hasUUID;
		if (nbt == null || (!(hasUUID = nbt.hasKey("OwnerUUID")) && !nbt.hasKey("Owner"))) {
			return "[None]";
		}
		return hasUUID ? stack.getTagCompound().getString("Owner") : StringUtil.localize("info.readonly.anotherplayer");
	}

}
