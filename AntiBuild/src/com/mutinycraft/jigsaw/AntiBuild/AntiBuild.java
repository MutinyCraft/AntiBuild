package com.mutinycraft.jigsaw.AntiBuild;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiBuild extends JavaPlugin implements Listener {

	Logger log;
	File configFile;
	File worldFile;
	FileConfiguration config;
	FileConfiguration wConfig;

	private boolean blacklistOn;
	private boolean usingLock;
	private boolean perBlockPermission;
	private List<String> messages;
	private List<String> lockedWorlds;
	private List<Integer> blacklist;
	private AntiBuildCommandExecutor cmdExecutor;
	private static final String VERSION = " v2.6";

	// Enable
	public void onEnable() {
		log = this.getLogger();

		try {
			configFile = new File(getDataFolder(), "config.yml");
			worldFile = new File(getDataFolder(), "lockedWorlds.yml");
			firstRun();
		} catch (Exception e) {
			e.printStackTrace();
		}

		config = new YamlConfiguration();
		wConfig = new YamlConfiguration();

		messages = new ArrayList<String>(10);

		loadYamls();

		setConfigOptions();
		setLockedWorlds();

		getServer().getPluginManager().registerEvents(this, this);

		new AntiBuildEventHandler(this);

		loadCommands();

		log.info(this.getName() + VERSION + " enabled!");
	}

	private void loadCommands() {
		cmdExecutor = new AntiBuildCommandExecutor(this);
		getCommand("antibuild").setExecutor(cmdExecutor);
	}

	// Config Handling
	private void setConfigOptions() {
		messages.add(0,
				ChatColor.translateAlternateColorCodes('&', (config.getString(
						"Message-Build", "&cYou are not allowed to build!"))));
		messages.add(1, ChatColor.translateAlternateColorCodes('&', (config
				.getString("Message-Break",
						"&cYou are not allowed to break blocks!"))));
		messages.add(2, ChatColor.translateAlternateColorCodes('&', (config
				.getString("Message-Bucket",
						"&cYou are not allowed to use buckets!"))));
		messages.add(3, ChatColor.translateAlternateColorCodes('&', (config
				.getString("Message-Chest",
						"&cYou are not allowed to access chests!"))));
		messages.add(4, ChatColor.translateAlternateColorCodes('&', (config
				.getString("Message-Interact",
						"&cYou are not allowed to interact with this!"))));
		messages.add(5, ChatColor.translateAlternateColorCodes('&', (config
				.getString("Message-Drop-Items",
						"&cYou are not allowed to drop items!"))));
		messages.add(6, ChatColor.translateAlternateColorCodes('&', (config
				.getString("Message-Blacklist",
						"&cYou may not place or break this type of block!"))));
		messages.add(
				7,
				ChatColor.translateAlternateColorCodes(
						'&',
						(config.getString("Message-Locked-World",
								"&cThis world is currently locked and cannot be built in."))));
		blacklistOn = config.getBoolean("Blacklist-On", false);
		blacklist = config.getIntegerList("Blacklisted-Blocks");
		perBlockPermission = config.getBoolean("Per-Block-Permission", false);
	}

	private void setLockedWorlds() {
		usingLock = false;
		lockedWorlds = wConfig.getStringList("Locked-Worlds");
		if (!lockedWorlds.isEmpty()) {
			usingLock = true;
		}
	}

	private void firstRun() throws Exception {
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			copy(getResource("config.yml"), configFile);
		}
		if (!worldFile.exists()) {
			worldFile.getParentFile().mkdirs();
			copy(getResource("lockedWorlds.yml"), worldFile);
		}
	}

	private void copy(InputStream in, File file) {
		try {
			OutputStream fout = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				fout.write(buf, 0, len);
			}
			fout.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadYamls() {
		try {
			config.load(configFile);
			wConfig.load(worldFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reloadConfig() {
		loadYamls();
		setConfigOptions();
	}

	// Message Handling

	public String getBuildMessage() {
		return messages.get(0);
	}

	public String getBreakMessage() {
		return messages.get(1);
	}

	public String getBucketMessage() {
		return messages.get(2);
	}

	public String getChestMessage() {
		return messages.get(3);
	}

	public String getInteractMessage() {
		return messages.get(4);
	}

	public String getDropItemsMessage() {
		return messages.get(5);
	}

	public String getBlackListMessage() {
		return messages.get(6);
	}

	public String getLockedWorldMessage() {
		return messages.get(7);
	}

	// Black List

	public boolean isBlacklistOn() {
		return blacklistOn;
	}

	public List<Integer> getBlacklist() {
		return blacklist;
	}

	public boolean isPerBlockPermission() {
		return perBlockPermission;
	}

	// World lock handling
	public boolean isUsingLock() {
		return usingLock;
	}

	public List<String> getLockedWorlds() {
		if (lockedWorlds.isEmpty()) {
			return null;
		}
		return lockedWorlds;
	}

	public void addLockedWorld(String worldName) {
		lockedWorlds.add(worldName);

		// Update Config

		getCustomConfig().set("Locked-Worlds", lockedWorlds);

		if (wConfig == null || worldFile == null) {
			return;
		}
		try {
			getCustomConfig().save(worldFile);
		} catch (IOException ex) {
			this.getLogger().log(Level.SEVERE,
					"Could not save config to " + worldFile, ex);
		}

		if (!lockedWorlds.isEmpty()) {
			usingLock = true;
		}

	}

	public void removeLockedWorld(String worldName) {
		lockedWorlds.remove(worldName);

		// Update Config

		getCustomConfig().set("Locked-Worlds", lockedWorlds);

		if (wConfig == null || worldFile == null) {
			return;
		}
		try {
			getCustomConfig().save(worldFile);
		} catch (IOException ex) {
			this.getLogger().log(Level.SEVERE,
					"Could not save config to " + worldFile, ex);
		}

		if (lockedWorlds.isEmpty()) {
			usingLock = false;
		}
	}

	public boolean isLockedWorld(String worldName) {
		if (lockedWorlds.isEmpty()) {
			return false;
		}
		return lockedWorlds.contains(worldName);
	}

	public FileConfiguration getCustomConfig() {
		if (wConfig == null) {
			this.reloadCustomConfig();
		}
		return wConfig;
	}

	public void reloadCustomConfig() {
		if (worldFile == null) {
			worldFile = new File(getDataFolder(), "customConfig.yml");
		}
		wConfig = YamlConfiguration.loadConfiguration(worldFile);

		InputStream defConfigStream = this.getResource("customConfig.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			wConfig.setDefaults(defConfig);
		}
	}

	// Disable
	public void onDispable() {
		log.info(this.getName() + VERSION + " disabled!");
	}
}
