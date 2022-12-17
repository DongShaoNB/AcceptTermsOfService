package cn.dsnbo.accepttermsofservice;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
            if (!AcceptTermsOfService.DataF.getBoolean(String.valueOf(player.getUniqueId()))) {
                Inventory inventory = Bukkit.createInventory(player, 9, ChatColor.translateAlternateColorCodes('&', AcceptTermsOfService.Plugin.getConfig().getString("gui.title")));
                ItemStack termsItemStack = new ItemStack(Material.getMaterial(AcceptTermsOfService.Plugin.getConfig().getString("gui.terms.blockid")));
                ItemMeta termsItemMeta = termsItemStack.getItemMeta();
                termsItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', AcceptTermsOfService.Plugin.getConfig().getString("gui.terms.name")));

                List<String> termsItemLore = AcceptTermsOfService.Plugin.getConfig().getStringList("gui.terms.lore");
                termsItemLore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
                termsItemMeta.setLore(termsItemLore);

                int termsItemStackSlot = AcceptTermsOfService.Plugin.getConfig().getInt("gui.terms.slot");
                termsItemStack.setItemMeta(termsItemMeta);
                inventory.setItem(termsItemStackSlot, termsItemStack);

                ItemStack acceptItemStack = new ItemStack(Material.getMaterial(AcceptTermsOfService.Plugin.getConfig().getString("gui.accept.blockid")));
                ItemMeta acceptItemMeta = acceptItemStack.getItemMeta();
                if (!AcceptTermsOfService.Plugin.getConfig().getStringList("gui.accept.lore").isEmpty()) {
                    List<String> acceptItemLore = AcceptTermsOfService.Plugin.getConfig().getStringList("gui.accept.lore");
                    acceptItemLore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
                    acceptItemMeta.setLore(acceptItemLore);
                }
                acceptItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', AcceptTermsOfService.Plugin.getConfig().getString("gui.accept.name")));
                int acceptItemStackSlot = AcceptTermsOfService.Plugin.getConfig().getInt("gui.accept.slot");
                acceptItemStack.setItemMeta(acceptItemMeta);
                inventory.setItem(acceptItemStackSlot, acceptItemStack);

                ItemStack rejectItemStack = new ItemStack(Material.getMaterial(AcceptTermsOfService.Plugin.getConfig().getString("gui.reject.blockid")));
                ItemMeta rejectItemMeta = rejectItemStack.getItemMeta();
                if (!AcceptTermsOfService.Plugin.getConfig().getStringList("gui.reject.lore").isEmpty()) {
                    List<String> rejectItemLore = AcceptTermsOfService.Plugin.getConfig().getStringList("gui.reject.lore");
                    rejectItemLore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
                    rejectItemMeta.setLore(rejectItemLore);
                }
                rejectItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', AcceptTermsOfService.Plugin.getConfig().getString("gui.reject.name")));
                int rejectItemStackSlot = AcceptTermsOfService.Plugin.getConfig().getInt("gui.reject.slot");
                rejectItemStack.setItemMeta(rejectItemMeta);
                inventory.setItem(rejectItemStackSlot, rejectItemStack);

                player.openInventory(inventory);
            }
        }
}
