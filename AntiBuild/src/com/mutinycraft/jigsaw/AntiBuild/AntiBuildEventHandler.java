package com.mutinycraft.jigsaw.AntiBuild;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class AntiBuildEventHandler implements Listener {

	private AntiBuild plugin;

	public AntiBuildEventHandler(AntiBuild pl) {
		this.plugin = pl;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Checks if a player has permission to place blocks.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void NoBuild(BlockPlaceEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPermission("antibuild.bypass")) {

			// Main check
			if (!player.hasPermission("antibuild.place")) {
				event.setCancelled(true);
			}

			// Flint and steel check
			if (!event.isCancelled() && event.getBlock().getTypeId() == 51) {
				if (!player.hasPermission("antibuild.fire")) {
					event.setCancelled(true);
				}
			}

			// Block Type Check
			if (plugin.isPerBlockPermission()) {
				if (player.hasPermission("antibuild.place."
						+ event.getBlock().getTypeId())) {
					event.setCancelled(false);
				}
			}

			if (event.isCancelled()) {
				messageHandler(plugin.getBuildMessage(), player);
			}
		}

		// Blacklist check
		if (!event.isCancelled() && plugin.isBlacklistOn()) {
			int id = event.getBlock().getTypeId();
			if (!player.hasPermission("antibuild.blacklist")
					&& blockIsBlacklisted(id)) {
				String perBlockPermission = "antibuild.blacklist."
						+ String.valueOf(id);
				if (!player.hasPermission(perBlockPermission)) {
					event.setCancelled(true);
					messageHandler(plugin.getBlackListMessage(), player);
				}
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageHandler(plugin.getLockedWorldMessage(), player);
			}
		}

	}

	/**
	 * Checks if a player has permission to break blocks.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void NoBreak(BlockBreakEvent event) {

		Player player = event.getPlayer();

		if (!player.hasPermission("antibuild.bypass")) {
			// Break check
			if (!player.hasPermission("antibuild.break")) {
				event.setCancelled(true);
			}

			// Block Type Check
			if (plugin.isPerBlockPermission()) {
				if (player.hasPermission("antibuild.break."
						+ event.getBlock().getTypeId())) {
					event.setCancelled(false);
				}
			}

			if (event.isCancelled()) {
				messageHandler(plugin.getBreakMessage(), player);
			}
		}

		// Blacklist check
		if (!event.isCancelled() && plugin.isBlacklistOn()) {
			int id = event.getBlock().getTypeId();
			if (!player.hasPermission("antibuild.blacklist")
					&& blockIsBlacklisted(id)) {
				String perBlockPermission = "antibuild.blacklist."
						+ String.valueOf(id);
				if (!player.hasPermission(perBlockPermission)) {
					event.setCancelled(true);
					messageHandler(plugin.getBlackListMessage(), player);
				}
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageHandler(plugin.getLockedWorldMessage(), player);
			}
		}
	}

	/**
	 * Checks if a player has permission to empty the contents of a bucket.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void NoBucketEmpty(PlayerBucketEmptyEvent event) {

		Player player = event.getPlayer();

		// Bucket check
		if (!player.hasPermission("antibuild.bypass")) {
			if (!player.hasPermission("antibuild.bucket")) {
				int bucketType = player.getItemInHand().getTypeId();
				if (bucketType == 326
						&& !player.hasPermission("antibuild.bucket.water")) {
					event.setCancelled(true);
					messageHandler(plugin.getBucketMessage(), player);
				} else if (bucketType == 327
						&& !player.hasPermission("antibuild.bucket.lava")) {
					event.setCancelled(true);
					messageHandler(plugin.getBucketMessage(), player);
				}
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageHandler(plugin.getLockedWorldMessage(), player);
			}
		}
	}

	/**
	 * Checks if a player has permission to fill a bucket.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void NoBucketFill(PlayerBucketFillEvent event) {

		Player player = event.getPlayer();

		// Bucket check
		if (!player.hasPermission("antibuild.bypass")) {
			if (!player.hasPermission("antibuild.bucket")) {
				int source = event.getBlockClicked().getTypeId();
				if ((source == 8 || source == 9)
						&& !player.hasPermission("antibuild.bucket.water")) {
					event.setCancelled(true);
					messageHandler(plugin.getBucketMessage(), player);
				} else if ((source == 10 || source == 11)
						&& !player.hasPermission("antibuild.bucket.lava")) {
					event.setCancelled(true);
					messageHandler(plugin.getBucketMessage(), player);
				}
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageHandler(plugin.getLockedWorldMessage(), player);
			}
		}
	}

	/**
	 * Checks if a player has permission to break paintings or item frames.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void NoHangingBreak(HangingBreakByEntityEvent event) {

		Entity entity = event.getRemover();
		Player player = null;

		if (event.getRemover().getType() == EntityType.PLAYER) {
			player = (Player) entity;
		}
		if (player != null) {

			if (!player.hasPermission("antibuild.bypass")) {
				if (!player.hasPermission("antibuild.painting")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
			}

			// World lock check
			if (!event.isCancelled() && plugin.isUsingLock()) {
				if (plugin.isLockedWorld(player.getWorld().getName())
						&& !player.hasPermission("antibuild.lock.bypass")) {
					event.setCancelled(true);
					messageHandler(plugin.getLockedWorldMessage(), player);
				}
			}
		}
	}

	/**
	 * Checks if a player has permission to hang paintings or item frames.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void NoHangingPlace(HangingPlaceEvent event) {

		Player player = event.getPlayer();

		// Hanging place
		if (!player.hasPermission("antibuild.bypass")) {
			if (!player.hasPermission("antibuild.painting")) {
				event.setCancelled(true);
				messageHandler(plugin.getInteractMessage(), player);
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageHandler(plugin.getLockedWorldMessage(), player);
			}
		}
	}

	/**
	 * Checks if a player has permission to open/accesss chests.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void NoChestAccess(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& event.getClickedBlock().getType() == Material.CHEST) {
			if (event.getPlayer() instanceof Player) {

				Player player = (Player) event.getPlayer();

				if (!player.hasPermission("antibuild.bypass")) {
					if (!player.hasPermission("antibuild.chest")) {
						event.setCancelled(true);
						messageHandler(plugin.getChestMessage(), player);
					}
				}

				// World lock check
				if (!event.isCancelled() && plugin.isUsingLock()) {
					if (plugin.isLockedWorld(player.getWorld().getName())
							&& !player.hasPermission("antibuild.lock.bypass")) {
						event.setCancelled(true);
						messageHandler(plugin.getLockedWorldMessage(), player);
					}
				}
			}
		}
	}

	/**
	 * Checks if player has proper permission to interact with inventory types.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void NoInventoryAccess(InventoryOpenEvent event) {
		Player player = null;

		if (event.getPlayer() instanceof Player) {
			player = (Player) event.getPlayer();
		}
		if (player != null && !player.hasPermission("antibuild.bypass")) {
			switch (event.getInventory().getType()) {
			case ANVIL:
				if (!player.hasPermission("antibuild.anvil")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case BEACON:
				if (!player.hasPermission("antibuild.beacon")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case BREWING:
				if (!player.hasPermission("antibuild.brewing")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case CHEST:
				if (!player.hasPermission("antibuild.chest")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case CRAFTING:
				if (!player.hasPermission("antibuild.crafting")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case DISPENSER:
				if (!player.hasPermission("antibuild.dispenser")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case ENCHANTING:
				if (!player.hasPermission("antibuild.enchanting")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case ENDER_CHEST:
				if (!player.hasPermission("antibuild.enderchest")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case HOPPER:
				if (!player.hasPermission("antibuild.hopper")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case FURNACE:
				if (!player.hasPermission("antibuild.furnace")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			case WORKBENCH:
				if (!player.hasPermission("antibuild.workbench")) {
					event.setCancelled(true);
					messageHandler(plugin.getInteractMessage(), player);
				}
				break;
			default:
				break;
			}
		}

		// World Check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageHandler(plugin.getLockedWorldMessage(), player);
			}
		}
	}

	/**
	 * Checks if player has proper permission to pickup items.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	private void PickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();

		// Pickup item check.
		if (player != null && !player.hasPermission("antibuild.bypass")) {
			if (!player.hasPermission("antibuild.pickupitems")) {
				event.setCancelled(true);
				// We can't message the player here or it spams.
			}
		}

		// World Check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				// We can't message the player here or it spams.
			}
		}
	}

	/**
	 * Checks if player has proper permission to drop items.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	private void DropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();

		// Drop item check.
		if (player != null && !player.hasPermission("antibuild.bypass")) {
			if (!player.hasPermission("antibuild.dropitems")) {
				event.setCancelled(true);
				messageHandler(plugin.getDropItemsMessage(), player);
			}
		}

		// World Check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageHandler(plugin.getLockedWorldMessage(), player);
			}
		}

	}

	/**
	 * Special check to see if an Entity (boat/minecart) is blacklisted since
	 * the normal blacklist will not check entities.
	 * 
	 * @param event
	 */
	@EventHandler(priority = EventPriority.LOW)
	private void EntityBlacklistCheck(PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Player player = event.getPlayer();
		int blockID = event.getPlayer().getItemInHand().getTypeId();

		if (plugin.isBlacklistOn()
				&& blockIsBlacklisted(blockID)
				&& (!player.hasPermission("antibuild.blacklist") || !player
						.hasPermission("antibuild.blacklist." + blockID))) {
			event.setCancelled(true);
			messageHandler(plugin.getBlackListMessage(), player);
		}
	}

	/**
	 * Checks the provided block to see if it is contained in the
	 * Blacklisted-Blocks section of the config.yml.
	 * 
	 * @param id
	 *            of block to check.
	 * @return true if block is blacklisted or false otherwise.
	 */
	private boolean blockIsBlacklisted(int id) {
		if (plugin.getBlacklist().contains(id)) {
			return true;
		}
		return false;
	}

	/**
	 * Handle messages that are displayed to players being denied permission for
	 * a specified action. This allows server admins to use an empty string in
	 * the config if they wish for no message to be sent.
	 * 
	 * @param msg
	 *            to send to player.
	 * @param player
	 *            to send the message to.
	 */
	private void messageHandler(String msg, Player player) {
		if (!msg.isEmpty()) {
			player.sendMessage(msg);
		}

	}

}
