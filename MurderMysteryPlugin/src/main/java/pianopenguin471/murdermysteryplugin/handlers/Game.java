package pianopenguin471.murdermysteryplugin.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pianopenguin471.murdermysteryplugin.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game {
	public List<Player> MURDERERS = new ArrayList<>();
	public Player DETECTIVE;
	public List<Player> INNOCENTS = new ArrayList<>();
	public Game() {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		int murderers;
		if (players.size() >= 5) {
			murderers = 2;
		} else {
			murderers = 1;
		}
		players.forEach(player -> player.setDisplayName("."));
		for (int i = 0; i < murderers; i++) {
			Player murderer = (Player) RandomUtils.getRandomObject(players);
			this.MURDERERS.add(murderer);
			murderer.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
			players.remove(murderer);
		}
		this.DETECTIVE = (Player) RandomUtils.getRandomObject(players);
		
		ItemStack bowItem = new ItemStack(Material.BOW);
		ItemMeta meta = bowItem.getItemMeta();
		meta.addEnchant(Enchantment.ARROW_INFINITE, 4,true);
		meta.setDisplayName(ChatColor.DARK_BLUE + "Detective's Bow");
		bowItem.setItemMeta(meta);
		
		this.DETECTIVE.getInventory().addItem(bowItem, new ItemStack(Material.ARROW));
		players.remove(this.DETECTIVE);
		for (Player innocent: players) {
			this.INNOCENTS.add(innocent);
		}
	}
}
