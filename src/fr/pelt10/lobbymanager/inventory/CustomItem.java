package fr.pelt10.lobbymanager.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.pelt10.lobbymanager.inventory.item.ItemAction;
import net.md_5.bungee.api.ChatColor;

public class CustomItem {
    private ItemStack item;
    private ItemAction itemAction;

    public CustomItem(Material material, String action, String name, String enchantName, int enchantLevel) {
	item = new ItemStack(material);
	// TODO action

	if (!name.isEmpty()) {
	    ItemMeta itemMeta = item.getItemMeta();
	    itemMeta.setDisplayName(ChatColor.RESET + name);
	    item.setItemMeta(itemMeta);
	}

	if(!enchantName.isEmpty() && enchantLevel > 0) {
	    item.addUnsafeEnchantment(Enchantment.getByName(enchantName), enchantLevel);
	}
    }

    public ItemStack getItem() {
	return item;
    }

    public ItemAction getItemAction() {
	return itemAction;
    }
}
