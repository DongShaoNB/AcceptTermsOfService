package cn.dsnbo.accepttermsofservice;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class AcceptTermsOfService extends JavaPlugin implements Listener {

    public static String Version = "1.0";
    public static Plugin Plugin;
    private final String[] SubCommands = {"help", "reload", "version"};
    public FileConfiguration Message;
    public static FileConfiguration DataF;
    public final Set<Player> playerHashSet = new HashSet<>();
    public boolean isAuthmeEnabled;
    public boolean isItemsAdderEnabled;


    @Override
    public void onEnable() {
        loadConfig();
        loadCommand();
        Plugin = AcceptTermsOfService.getProvidingPlugin(AcceptTermsOfService.class);
        isAuthmeEnabled = !(Bukkit.getPluginManager().getPlugin("Authme") == null);
        isItemsAdderEnabled = !(Bukkit.getPluginManager().getPlugin("ItemsAdder") == null);
        if (isAuthmeEnabled) {
            Bukkit.getPluginManager().registerEvents(new AuthmeListener(), this);
        } else if (isItemsAdderEnabled || getConfig().getInt("mode") == 1) {
            Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        } else if (getConfig().getInt("mode") == 0){
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        } else {
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        }
        Bukkit.getPluginManager().registerEvents(this, this);
        new Metrics(this, 17068);
        getLogger().info("感谢选择使用本插件，作者: DongShaoNB，QQ群: 159323818");

    }

    private void loadConfig() {
        saveDefaultConfig();
        File MessageFile = new File(getDataFolder(), "message.yml");
        File DataFile = new File(getDataFolder(), "data.yml");
        if (!MessageFile.exists()) {
            saveResource("message.yml", false);
        }

        if (!DataFile.exists()) {
            saveResource("data.yml", false);
        }
        Message = YamlConfiguration.loadConfiguration(MessageFile);
        DataF = YamlConfiguration.loadConfiguration(DataFile);
    }

    private void writeData(String key, Object value) throws IOException {
        File DataFile = new File(getDataFolder(), "data.yml");
        FileConfiguration DataConfig = YamlConfiguration.loadConfiguration(DataFile);
        DataConfig.set(key, value);
        DataConfig.save(DataFile);
        DataFile = new File(getDataFolder(), "data.yml");
        DataF = YamlConfiguration.loadConfiguration(DataFile);
    }

    private void loadCommand() {
        getCommand("atos").setExecutor(new MainCommand());
        getCommand("atos").setTabCompleter(this);
    }


    @Override
    public void onDisable() {

    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) throws IOException {
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.title")))) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getRawSlot() == getConfig().getInt("gui.accept.slot")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Message.getString("prefix") + Message.getString("accept")));
                playerHashSet.add(p);
                p.closeInventory();
                playerHashSet.remove(p);
                writeData(String.valueOf(p.getUniqueId()), true);
            } else if (e.getRawSlot() == getConfig().getInt("gui.reject.slot")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Message.getString("prefix") + Message.getString("reject")));
                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Message.getString("reject")));
            }
        }
    }

    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent e) {
        if (e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', getConfig().getString("gui.title")))) {
            Player p = (Player) e.getPlayer();
            if (!playerHashSet.contains(p)) {
                p.kickPlayer(ChatColor.translateAlternateColorCodes('&', Message.getString("prefix") + Message.getString("reject")));
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 1) return new ArrayList<>();

        if (args.length == 0) return Arrays.asList(SubCommands);

        return Arrays.stream(SubCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
    }
}