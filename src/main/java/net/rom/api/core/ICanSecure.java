package net.rom.api.core;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Implemention interface for any Object that will have Access restriction
 * capabilities
 * 
 * @author ROMVoid
 * @since 1.0.0
 */
public interface ICanSecure {

	/**
	 * The AccessState of the Object.
	 */
	enum AccessState {

		/** Access granted to all */
		OPEN,
		/** Access granted to trusdted players only */
		TRUSTED,
		/** Access granted to Clan only */
		CLAN,
		/** Access granted to Onwer only */
		PRIVATE,
		/** Access Denied to all */
		LOCKDOWN;

		/**
		 * Checks if access is open.
		 *
		 * @return true, if is open
		 */
		public boolean isOpen() {
			return this == OPEN;
		}

		/**
		 * Checks if access is trusted players only.
		 *
		 * @return true, if is trusted only
		 */
		public boolean isTrustedOnly() {
			return this == TRUSTED;
		}

		/**
		 * Checks if access is clan only.
		 *
		 * @return true, if is clan only
		 */
		public boolean isClanOnly() {
			return this == CLAN;
		}

		/**
		 * Checks if access is private.
		 *
		 * @return true, if is private
		 */
		public boolean isPrivate() {
			return this == PRIVATE;
		}

		/**
		 * Checks if access is denied globally.
		 *
		 * @return true, if is locked down
		 */
		public boolean isLockedDown() {
			return this == LOCKDOWN;
		}

		/**
		 * Increase security.
		 *
		 * @param accessState the access state
		 * @return the access state
		 */
		public static AccessState increaseSecurity(AccessState accessState) {
			return accessState == OPEN ? TRUSTED
					: accessState == TRUSTED ? CLAN
							: accessState == CLAN ? PRIVATE : accessState == PRIVATE ? LOCKDOWN : OPEN;
		}

		/**
		 * Decrease security.
		 *
		 * @param accessState the access state
		 * @return the access state
		 */
		public static AccessState decreaseSecurity(AccessState accessState) {
			return accessState == OPEN ? LOCKDOWN
					: accessState == LOCKDOWN ? PRIVATE
							: accessState == PRIVATE ? CLAN : accessState == CLAN ? TRUSTED : OPEN;
		}
	}

	/**
	 * Can access.
	 *
	 * @param player the player
	 * @return true, if successful
	 */
	boolean canAccess(EntityPlayer player);

	/**
	 * Sets the access.
	 *
	 * @param accessState the access state
	 * @return true, if successful
	 */
	boolean setAccess(AccessState accessState);

	/**
	 * Sets the owner name.
	 *
	 * @param ownerName the owner name
	 * @return true, if successful
	 */
	boolean setOwnerName(String ownerName);

	/**
	 * Sets the owner.
	 *
	 * @param owner the owner
	 * @return true, if successful
	 */
	boolean setOwner(GameProfile owner);
	
	
	AccessState getAccess();

	/**
	 * Gets the owners name.
	 *
	 * @return the owners name
	 */
	String getOwnersName();

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	GameProfile getOwner();

}
