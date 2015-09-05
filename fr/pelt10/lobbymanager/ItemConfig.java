package fr.pelt10.lobbymanager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemConfig {
	ItemStack item;
	String command;

	public ItemConfig(String block, String cmd, String name, String enchantName, int enchantLevel) {

		item = new ItemStack(Material.getMaterial(block));
		item.addUnsafeEnchantment(Enchantment.getByName(enchantName), enchantLevel);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(name);
		item.setItemMeta(itemM);

		command = cmd;
	}

	public ItemConfig(String block, String cmd, String name) {
		item = new ItemStack(Material.getMaterial(block));
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(name);
		item.setItemMeta(itemM);

		command = cmd;
	}

	public ItemConfig(String block, String cmd) {
		item = new ItemStack(Material.getMaterial(block));

		command = cmd;
	}

	public ItemStack getItem() {
		return item;
	}

	public String getCommand() {
		return command;
	}
}
