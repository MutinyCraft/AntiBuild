package com.mutinycraft.jigsaw.AntiBuild;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private List<Integer> blacklistPlace;
    private List<Integer> blacklistBreak;
    private AntiBuildCommandExecutor cmdExecutor;

    public void onEnable() {
        log = this.getLogger();
        cmdExecutor = new AntiBuildCommandExecutor(this);
        config = new YamlConfiguration();
        wConfig = new YamlConfiguration();
        messages = new ArrayList<String>(10);

        try {
            configFile = new File(getDataFolder(), "config.yml");
            worldFile = new File(getDataFolder(), "lockedWorlds.yml");
            firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadYamls();
        setConfigOptions();
        setLockedWorlds();

        getServer().getPluginManager().registerEvents(this, this);
        new AntiBuildEventHandler(this);

        getCommand("antibuild").setExecutor(cmdExecutor);

        log.info(this.getName() + " enabled!");
    }

    /**
     * Load config options by getting them from the config.yml.  If the values are not found use the default value
     * provided.
     */
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
        blacklistPlace = config.getIntegerList("Blacklisted-Blocks-Place");
        blacklistBreak = config.getIntegerList("Blacklisted-Blocks-Break");
        perBlockPermission = config.getBoolean("Per-Block-Permission", false);
    }

    /**
     * Load the locked worlds from disk.
     */
    private void setLockedWorlds() {
        usingLock = false;
        lockedWorlds = wConfig.getStringList("Locked-Worlds");
        if (!lockedWorlds.isEmpty()) {
            usingLock = true;
        }
    }

    /**
     * Attempts to create the config.yml and lockedWorlds.yml if they do not already exist.
     *
     * @throws Exception
     */
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

    /**
     * Copy data in memory to file.
     *
     * @param in
     * @param file
     */
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

    /**
     * Load yamls into memory.
     */
    private void loadYamls() {
        try {
            config.load(configFile);
            wConfig.load(worldFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reloads config.yml which allows changes to be made while server is running.
     */
    @Override
    public void reloadConfig() {
        loadYamls();
        setConfigOptions();
    }

    /**
     * Get the locked worlds config.
     *
     * @return custom config.
     */
    public FileConfiguration getCustomConfig() {
        if (wConfig == null) {
            this.reloadCustomConfig();
        }
        return wConfig;
    }

    /**
     * Reload locked worlds file.
     */
    public void reloadCustomConfig() {
        if (worldFile == null) {
            worldFile = new File(getDataFolder(), "lockedWorlds.yml");
        }
        wConfig = YamlConfiguration.loadConfiguration(worldFile);

        InputStream defConfigStream = this.getResource("lockedWorlds.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration
                    .loadConfiguration(defConfigStream);
            wConfig.setDefaults(defConfig);
        }
    }

    /**
     * Message to send when building is denied.
     *
     * @return message
     */
    public String getBuildMessage() {
        return messages.get(0);
    }

    /**
     * Message to send when breaking is denied.
     *
     * @return message
     */
    public String getBreakMessage() {
        return messages.get(1);
    }

    /**
     * Message to send when bucket use is denied.
     *
     * @return message
     */
    public String getBucketMessage() {
        return messages.get(2);
    }

    /**
     * Message to send when chest interaction is denied.
     *
     * @return message
     */
    public String getChestMessage() {
        return messages.get(3);
    }

    /**
     * Message to send when inventory interaction is denied.
     *
     * @return message
     */
    public String getInteractMessage() {
        return messages.get(4);
    }

    /**
     * Message to send when item drop is denied.
     *
     * @return message
     */
    public String getDropItemsMessage() {
        return messages.get(5);
    }

    /**
     * Message to send when blacklisted block is used.
     *
     * @return message
     */
    public String getBlackListMessage() {
        return messages.get(6);
    }

    /**
     * Message to send when interaction is denied in a locked world.
     *
     * @return message
     */
    public String getLockedWorldMessage() {
        return messages.get(7);
    }

    /**
     * Checks whether the blacklist is enabled in the config.yml.
     *
     * @return true if enabled, false otherwise.
     */
    public boolean isBlacklistOn() {
        return blacklistOn;
    }

    /**
     * Returns a list of all blacklisted block IDs for placing.
     *
     * @return list of blacklisted blocks.
     */
    public List<Integer> getBlacklistPlace() {
        return blacklistPlace;
    }

    /**
     * Returns a list of all blacklisted block IDs for breaking.
     *
     * @return list of blacklisted blocks.
     */
    public List<Integer> getBlacklistBreak() {
        return blacklistBreak;
    }

    /**
     * Check whether per block permission is enabled in the config.yml.
     *
     * @return true if enabled, false otherwise.
     */
    public boolean isPerBlockPermission() {
        return perBlockPermission;
    }

    /**
     * Check whether world lock is being used.
     *
     * @return true if enabled, false otherwise.
     */
    public boolean isUsingLock() {
        return usingLock;
    }

    /**
     * Returns list of all locked worlds.
     *
     * @return list of locked worlds.
     */
    public List<String> getLockedWorlds() {
        if (lockedWorlds.isEmpty()) {
            return null;
        }
        return lockedWorlds;
    }

    /**
     * Add a locked world to the list of locked worlds.
     *
     * @param worldName of world to lock.
     */
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

    /**
     * Remove locked world from list of locked worlds.
     *
     * @param worldName of world to unlock.
     */
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

    /**
     * Check whether a world is locked.
     *
     * @param worldName of world to check.
     * @return true if locked, false otherwise.
     */
    public boolean isLockedWorld(String worldName) {
        if (lockedWorlds.isEmpty()) {
            return false;
        }
        return lockedWorlds.contains(worldName);
    }

    public void onDispable() {
        log.info(this.getName() + " disabled!");
    }
}
