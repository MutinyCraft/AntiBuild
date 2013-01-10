package com.mutinycraft.jigsaw.AntiBuild;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AntiBuildEventHandler implements Listener{
	
	private AntiBuild plugin;
	
	public AntiBuildEventHandler(AntiBuild pl){
		this.plugin = pl;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	//Events
	@EventHandler(priority = EventPriority.LOW)
	public void NoBuild(BlockPlaceEvent event){
		Player player = event.getPlayer();
		boolean cancelled = false;
		
		if(!player.hasPermission("antibuild.bypass")){
		
			// Main check
				if(!player.hasPermission("antibuild.place")){
					event.setCancelled(true);
					message(player);
					cancelled = true;
				}
			
			// Flint and steel check
			if(!cancelled && event.getBlock().getTypeId() == 51){
				if(!player.hasPermission("antibuild.fire")){
					event.setCancelled(true);
					message(player);
					cancelled = true;
				}
			}			
		}
		
		// Blacklist check
		if(!cancelled && plugin.isBlacklistOn()){
			int id = event.getBlock().getTypeId();
			if(!player.hasPermission("antibuild.blacklist") && blockIsBlacklisted(id)){
				String perBlockPermission = "antibuild.blacklist." + String.valueOf(id);
				if(!player.hasPermission(perBlockPermission)){
					event.setCancelled(true);
					messageBlacklist(player);
					cancelled = true;
				}
			}
		}
		
		// World lock check
		if(!cancelled && plugin.isUsingLock()){
			if(plugin.isLockedWorld(player.getWorld().getName()) && !player.hasPermission("antibuild.lock.bypass")){
				event.setCancelled(true);
				messageWorld(player);
				cancelled = true;
			}
		}
		
	}

	@EventHandler(priority = EventPriority.LOW)
	public void NoBreak(BlockBreakEvent event){
		
		Player player = event.getPlayer();
		boolean cancelled = false;
		
		if(!player.hasPermission("antibuild.bypass")){
			// Break check
			if(!player.hasPermission("antibuild.break")){
				event.setCancelled(true);
				message(player);
				cancelled = true;
			}
		}
		
		// Blacklist check
		if(!cancelled && plugin.isBlacklistOn()){
			int id = event.getBlock().getTypeId();
			if(!player.hasPermission("antibuild.blacklist") && blockIsBlacklisted(id)){
				String perBlockPermission = "antibuild.blacklist." + String.valueOf(id);
				if(!player.hasPermission(perBlockPermission)){
					event.setCancelled(true);
					messageBlacklist(player);
					cancelled = true;
				}
			}
		}
		
		// World lock check
		if(!cancelled && plugin.isUsingLock()){
			if(plugin.isLockedWorld(player.getWorld().getName()) && !player.hasPermission("antibuild.lock.bypass")){
				event.setCancelled(true);
				messageWorld(player);
				cancelled = true;
			}
		}
	}
	
	// Bucket Interaction
	
	@EventHandler(priority = EventPriority.LOW)
	public void NoBucketEmpty(PlayerBucketEmptyEvent event){
		
		Player player = event.getPlayer();
		boolean cancelled = false;
		
		// Bucket check
		if(!player.hasPermission("antibuild.bypass")){
			if(!player.hasPermission("antibuild.bucket")){
				event.setCancelled(true);
				message(player);
				cancelled = true;
			}	
		}
		
		// World lock check
		if(!cancelled && plugin.isUsingLock()){
			if(plugin.isLockedWorld(player.getWorld().getName()) && !player.hasPermission("antibuild.lock.bypass")){
				event.setCancelled(true);
				messageWorld(player);
				cancelled = true;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void NoBucketFill(PlayerBucketFillEvent event){
		
		Player player = event.getPlayer();
		boolean cancelled = false;
		
		// Bucket check
		if(!player.hasPermission("antibuild.bypass")){
			if(!player.hasPermission("antibuild.bucket")){
				event.setCancelled(true);
				message(player);
				cancelled = true;
			}
		}
		
		// World lock check
		if(!cancelled && plugin.isUsingLock()){
			if(plugin.isLockedWorld(player.getWorld().getName()) && !player.hasPermission("antibuild.lock.bypass")){
				event.setCancelled(true);
				messageWorld(player);
				cancelled = true;
			}
		}
	}
	
	// Painting/ItemFrame Interaction
	
	@EventHandler(priority = EventPriority.LOW)
	public void NoHangingBreak(HangingBreakByEntityEvent  event){
		
		Entity entity = event.getRemover();
		Player player = null;
		boolean cancelled = false;
		
		if(event.getRemover().getType() == EntityType.PLAYER){
			player = (Player) entity;
		}
		if(player != null){
			
			if(!player.hasPermission("antibuild.bypass")){
				if(!player.hasPermission("antibuild.painting")){
					event.setCancelled(true);
					message(player);
					cancelled = true;
				}
			}
			
			// World lock check
			if(!cancelled && plugin.isUsingLock()){
				if(plugin.isLockedWorld(player.getWorld().getName()) && !player.hasPermission("antibuild.lock.bypass")){
					event.setCancelled(true);
					messageWorld(player);
					cancelled = true;
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void NoHangingPlace(HangingPlaceEvent  event){
		
		Player player = event.getPlayer();
		boolean cancelled = false;
		
		// Hanging place
		if(!player.hasPermission("antibuild.bypass")){
			if(!player.hasPermission("antibuild.painting")){
				event.setCancelled(true);
				message(player);
				cancelled = true;
			}
		}
				
		// World lock check
		if(!cancelled && plugin.isUsingLock()){
			if(plugin.isLockedWorld(player.getWorld().getName()) && !player.hasPermission("antibuild.lock.bypass")){
				event.setCancelled(true);
				messageWorld(player);
				cancelled = true;
			}
		}
	}
	
	// Chest Access 
	
	@EventHandler(priority = EventPriority.LOW)
	public void NoChestAccess(PlayerInteractEvent  event){
		
		Material block;
		boolean cancelled = false;
		
		try{
			block = event.getClickedBlock().getType();
		}catch(NullPointerException e){
			block = null;
		}
		
		if(block == Material.CHEST){
			
			Entity entity = event.getPlayer();
			Player player = null;
			
			if(entity instanceof Player){
				player = (Player) entity;
			}
	
			if(player != null){
				
				if(!player.hasPermission("antibuild.bypass")){
					if(!player.hasPermission("antibuild.chest")){
						event.setCancelled(true);
						message(player);
						cancelled = true;
					}
				}
				
				// World lock check
				if(!cancelled && plugin.isUsingLock()){
					if(plugin.isLockedWorld(player.getWorld().getName()) && !player.hasPermission("antibuild.lock.bypass")){
						event.setCancelled(true);
						messageWorld(player);
						cancelled = true;
					}
				}
			}
		}
	}	
	
	// Blacklist 
	
	private boolean blockIsBlacklisted(int id) {
		if(plugin.getBlacklist().contains(id)){
			return true;
		}
		return false;
	}
	
	// Messages
	
	public void message(Player p){
		p.sendMessage(plugin.getMessage());
	}
	
	public void messageBlacklist(Player p){
		p.sendMessage(plugin.getBlacklistMessage());
	}

	public void messageWorld(Player p){
		p.sendMessage(plugin.getLockedWorldMessage());
	}

}
