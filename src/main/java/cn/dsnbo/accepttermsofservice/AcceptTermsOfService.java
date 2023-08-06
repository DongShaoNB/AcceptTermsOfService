package cn.dsnbo.accepttermsofservice;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DongShaoNB
 */
public final class AcceptTermsOfService extends JavaPlugin implements Listener {

    public static String Version = "1.6";
    public static Plugin Plugin;
    private final String[] SubCommands = {"help", "reload", "version"};
    public FileConfiguration message;
    public static FileConfiguration DataF;


    @Override
    public void onEnable() {
        loadConfig();
        loadCommand();
        Plugin = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        new Metrics(this, 17068);
        getLogger().info("感谢选择使用本插件，作者: DongShaoNB，QQ群: 159323818");

    }

    private void loadConfig() {
        saveDefaultConfig();
        File messageFile = new File(getDataFolder(), "message.yml");
        File dataFile = new File(getDataFolder(), "data.yml");
        if (!messageFile.exists()) {
            saveResource("message.yml", false);
        }

        if (!dataFile.exists()) {
            saveResource("data.yml", false);
        }
        message = YamlConfiguration.loadConfiguration(messageFile);
        DataF = YamlConfiguration.loadConfiguration(dataFile);
    }

    private void writeData(String key, Object value) {
        File dataFile = new File(getDataFolder(), "data.yml");
        FileConfiguration dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        dataConfig.set(key, value);
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataFile = new File(getDataFolder(), "data.yml");
        DataF = YamlConfiguration.loadConfiguration(dataFile);
    }

    private void loadCommand() {
        getCommand("atos").setExecutor(new MainCommand());
        getCommand("atos").setTabCompleter(this);
    }


    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.title")))) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getRawSlot() == getConfig().getInt("gui.accept.slot")) {
                p.closeInventory();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.getString("prefix") + message.getString("accept")));
                writeData(String.valueOf(p.getUniqueId()), true);
            } else if (e.getRawSlot() == getConfig().getInt("gui.reject.slot")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.getString("prefix") + message.getString("reject")));
                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', message.getString("reject")));
            }
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (DataF.get(String.valueOf(player.getUniqueId())) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.getString("prefix") + message.getString("tip")));
            InventoryGUI.openAtosGui(player);
            event.setCancelled(true);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length > 1) {
            return new ArrayList<>();
        }

        if (args.length == 0) {
            return Arrays.asList(SubCommands);
        }

        return Arrays.stream(SubCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }
}