package cn.dsnbo.accepttermsofservice;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
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

            if (!(AcceptTermsOfService.Plugin.getConfig().getString("gui.terms2.blockid") == null)) {
                ItemStack terms2ItemStack = new ItemStack(Material.getMaterial(AcceptTermsOfService.Plugin.getConfig().getString("gui.terms2.blockid")));
                ItemMeta terms2ItemMeta = terms2ItemStack.getItemMeta();
                terms2ItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', AcceptTermsOfService.Plugin.getConfig().getString("gui.terms2.name")));
                List<String> terms2ItemLore = AcceptTermsOfService.Plugin.getConfig().getStringList("gui.terms2.lore");
                terms2ItemLore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
                terms2ItemMeta.setLore(terms2ItemLore);
                int terms2ItemStackSlot = AcceptTermsOfService.Plugin.getConfig().getInt("gui.terms2.slot");
                terms2ItemStack.setItemMeta(terms2ItemMeta);
                inventory.setItem(terms2ItemStackSlot, terms2ItemStack);
            }

            if (!(AcceptTermsOfService.Plugin.getConfig().getString("gui.terms3.blockid") == null)) {
                ItemStack terms3ItemStack = new ItemStack(Material.getMaterial(AcceptTermsOfService.Plugin.getConfig().getString("gui.terms3.blockid")));
                ItemMeta terms3ItemMeta = terms3ItemStack.getItemMeta();
                terms3ItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', AcceptTermsOfService.Plugin.getConfig().getString("gui.terms3.name")));
                List<String> terms3ItemLore = AcceptTermsOfService.Plugin.getConfig().getStringList("gui.terms3.lore");
                terms3ItemLore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
                terms3ItemMeta.setLore(terms3ItemLore);
                int terms3ItemStackSlot = AcceptTermsOfService.Plugin.getConfig().getInt("gui.terms3.slot");
                terms3ItemStack.setItemMeta(terms3ItemMeta);
                inventory.setItem(terms3ItemStackSlot, terms3ItemStack);
            }


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
