package com.mutinycraft.jigsaw.AntiBuild;

import org.bukkit.ChatColor;
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
import org.bukkit.event.player.PlayerInteractEvent;

public class AntiBuildEventHandler implements Listener {

	private AntiBuild plugin;

	public AntiBuildEventHandler(AntiBuild pl) {
		this.plugin = pl;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	// Events
	@EventHandler(priority = EventPriority.LOW)
	public void NoBuild(BlockPlaceEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPermission("antibuild.bypass")) {

			// Main check
			if (!player.hasPermission("antibuild.place")) {
				event.setCancelled(true);
				message(player);
			}

			// Flint and steel check
			if (!event.isCancelled() && event.getBlock().getTypeId() == 51) {
				if (!player.hasPermission("antibuild.fire")) {
					event.setCancelled(true);
					message(player);
				}
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
					messageBlacklist(player);
				}
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageWorld(player);
			}
		}

	}

	@EventHandler(priority = EventPriority.LOW)
	public void NoBreak(BlockBreakEvent event) {

		Player player = event.getPlayer();

		if (!player.hasPermission("antibuild.bypass")) {
			// Break check
			if (!player.hasPermission("antibuild.break")) {
				event.setCancelled(true);
				message(player);
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
					messageBlacklist(player);
				}
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageWorld(player);
			}
		}
	}

	// Bucket Interaction

	@EventHandler(priority = EventPriority.LOW)
	public void NoBucketEmpty(PlayerBucketEmptyEvent event) {

		Player player = event.getPlayer();

		// Bucket check
		if (!player.hasPermission("antibuild.bypass")) {
			if (!player.hasPermission("antibuild.bucket")) {
				event.setCancelled(true);
				message(player);
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageWorld(player);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void NoBucketFill(PlayerBucketFillEvent event) {

		Player player = event.getPlayer();

		// Bucket check
		if (!player.hasPermission("antibuild.bypass")) {
			if (!player.hasPermission("antibuild.bucket")) {
				event.setCancelled(true);
				message(player);
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageWorld(player);
			}
		}
	}

	// Painting/ItemFrame Interaction

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
					message(player);
				}
			}

			// World lock check
			if (!event.isCancelled() && plugin.isUsingLock()) {
				if (plugin.isLockedWorld(player.getWorld().getName())
						&& !player.hasPermission("antibuild.lock.bypass")) {
					event.setCancelled(true);
					messageWorld(player);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void NoHangingPlace(HangingPlaceEvent event) {

		Player player = event.getPlayer();

		// Hanging place
		if (!player.hasPermission("antibuild.bypass")) {
			if (!player.hasPermission("antibuild.painting")) {
				event.setCancelled(true);
				message(player);
			}
		}

		// World lock check
		if (!event.isCancelled() && plugin.isUsingLock()) {
			if (plugin.isLockedWorld(player.getWorld().getName())
					&& !player.hasPermission("antibuild.lock.bypass")) {
				event.setCancelled(true);
				messageWorld(player);
			}
		}
	}

	// Chest Access

	@EventHandler(priority = EventPriority.LOW)
	public void NoChestAccess(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& event.getClickedBlock().getType() == Material.CHEST) {
			if (event.getPlayer() instanceof Player) {

				Player player = (Player) event.getPlayer();

				if (!player.hasPermission("antibuild.bypass")) {
					if (!player.hasPermission("antibuild.chest")) {
						event.setCancelled(true);
						message(player);
					}
				}

				// World lock check
				if (!event.isCancelled() && plugin.isUsingLock()) {
					if (plugin.isLockedWorld(player.getWorld().getName())
							&& !player.hasPermission("antibuild.lock.bypass")) {
						event.setCancelled(true);
						messageWorld(player);
					}
				}
			}
		}
	}

	// Inventory Access

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
					message(player);
				}
				break;
			case BEACON:
				if (!player.hasPermission("antibuild.beacon")) {
					event.setCancelled(true);
					message(player);
				}
				break;
			case BREWING:
				if (!player.hasPermission("antibuild.brewing")) {
					event.setCancelled(true);
					message(player);
				}
				break;
			case CHEST:
				if (!player.hasPermission("antibuild.chest")) {
					event.setCancelled(true);
					message(player);
				}
				break;
			case CRAFTING:
				if (!player.hasPermission("antibuild.crafting")) {
					event.setCancelled(true);
					message(player);
				}
				break;
			case DISPENSER:
				if (!player.hasPermission("antibuild.dispenser")) {
					event.setCancelled(true);
					message(player);
				}
				break;
			case ENCHANTING:
				if (!player.hasPermission("antibuild.enchanting")) {
					event.setCancelled(true);
					message(player);
				}
				break;
			case ENDER_CHEST:
				if (!player.hasPermission("antibuild.enderchest")) {
					event.setCancelled(true);
					message(player);
				}
				break;
			case FURNACE:
				if (!player.hasPermission("antibuild.furnace")) {
					event.setCancelled(true);
					message(player);
				}
				break;
			case WORKBENCH:
				if (!player.hasPermission("antibuild.workbench")) {
					event.setCancelled(true);
					message(player);
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
				messageWorld(player);
			}
		}
	}

	// Blacklist

	private boolean blockIsBlacklisted(int id) {
		if (plugin.getBlacklist().contains(id)) {
			return true;
		}
		return false;
	}

	// Messages

	public void message(Player p) {
		p.sendMessage(plugin.getMessage());
	}

	public void messageBlacklist(Player p) {
		p.sendMessage(plugin.getBlacklistMessage());
	}

	public void messageWorld(Player p) {
		p.sendMessage(plugin.getLockedWorldMessage());
	}

}
