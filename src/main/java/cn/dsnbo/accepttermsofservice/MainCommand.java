package cn.dsnbo.accepttermsofservice;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

import static org.bukkit.Bukkit.getLogger;

/**
 * @author DongShaoNB
 */
public class MainCommand implements CommandExecutor {

    public YamlConfiguration message;
    public String prefix;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        File messageFile = new File(AcceptTermsOfService.Plugin.getDataFolder(), "message.yml");
        message = YamlConfiguration.loadConfiguration(messageFile);
        prefix = message.getString("prefix");
        if (sender instanceof Player) {
            if (sender.hasPermission("atos.admin")){
                PlayerCommand((Player) sender, args);
            }
        } else {
            ConsoleCommand(args);
        }
        return false;
    }

    public boolean PlayerCommand(Player player, String[] Args){
        if (Args.length == 0){
            player.sendMessage(ChatColor.AQUA + "=========================");
            player.sendMessage(ChatColor.GOLD + "AcceptTermsOfService  v" + AcceptTermsOfService.Version);
            player.sendMessage(ChatColor.GREEN + "/atos reload       重载配置文件");
            player.sendMessage(ChatColor.GREEN + "/atos version      查看版本信息");
            player.sendMessage(ChatColor.AQUA + "=========================");
        } else {
            switch (Args[0]) {
                case "version":
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.GREEN + " 当前版本: " + AcceptTermsOfService.Version);
                    break;
                case "reload":
                    AcceptTermsOfService.Plugin.reloadConfig();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.translateAlternateColorCodes('&', message.getString("reload")));
                    break;
                case "help":
                default:
                    player.sendMessage(ChatColor.AQUA + "=========================");
                    player.sendMessage(ChatColor.GOLD + "AcceptTermsOfService  v" + AcceptTermsOfService.Version);
                    player.sendMessage(ChatColor.GREEN + "/atos reload       重载配置文件");
                    player.sendMessage(ChatColor.GREEN + "/atos version      查看版本信息");
                    player.sendMessage(ChatColor.AQUA + "=========================");
                    break;

            }
        }
        return false;
    }

    public boolean ConsoleCommand(String[] Args){
        if (Args.length == 0){
            getLogger().info("============================");
            getLogger().info("AcceptTermsOfService  v" + AcceptTermsOfService.Version);
            getLogger().info("/atos reload       重载配置文件");
            getLogger().info("/atos version      查看版本信息");
            getLogger().info("============================");
        } else {
            switch (Args[0]) {
                case "version":
                    getLogger().info("当前版本: " + AcceptTermsOfService.Version);
                    break;
                case "reload":
                    AcceptTermsOfService.Plugin.reloadConfig();
                    getLogger().info("重载成功!");
                    break;
                case "help":
                default:
                    getLogger().info("============================");
                    getLogger().info("AcceptTermsOfService  v" + AcceptTermsOfService.Version);
                    getLogger().info("/atos reload       重载配置文件");
                    getLogger().info("/atos version      查看版本信息");
                    getLogger().info("============================");
                    break;

            }
        }
        return false;
    }
}
