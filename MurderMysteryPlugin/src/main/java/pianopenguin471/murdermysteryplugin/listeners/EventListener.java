package pianopenguin471.murdermysteryplugin.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import pianopenguin471.murdermysteryplugin.handlers.Game;
import pianopenguin471.murdermysteryplugin.Main;

public class EventListener implements Listener {
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if (Main.currentGame != null) {
			Game currentGame = Main.currentGame;
			if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
				if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
					Player attacker = (Player) event.getDamager();
					Player attacked = (Player) event.getEntity();
					if (attacker.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD) {
						if (currentGame.INNOCENTS.contains(attacked)) {
							attacked.setGameMode(GameMode.SPECTATOR);
							attacked.getInventory().clear();
							attacker.getWorld().playSound(attacker.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 5);
							currentGame.INNOCENTS.remove(attacked);
							
							if (currentGame.INNOCENTS.isEmpty() && currentGame.DETECTIVE == null) {
								for (Player player : Bukkit.getOnlinePlayers()) {
									player.sendTitle(ChatColor.DARK_RED + "All innocents have been killed, murderers win", "", 10, 70, 20);
									Main.currentGame = null;
								}
							}
							
						} else if (currentGame.DETECTIVE == attacked) {
							attacked.setGameMode(GameMode.SPECTATOR);
							attacked.getInventory().clear();
							attacker.getWorld().playSound(attacker.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 5);
							currentGame.DETECTIVE = null;
							attacker.getWorld().dropItem(attacked.getLocation(), new ItemStack(Material.BOW));
							
							for (Player player2 : currentGame.INNOCENTS) {
								player2.sendTitle(ChatColor.GREEN + "The Detective has been killed", "Pick up the bow", 10, 70, 20);
							}
							for (Player murderer : currentGame.MURDERERS) {
								murderer.sendTitle(ChatColor.GREEN + "The Detective has been killed", "Go get them", 10, 70, 20);
							}
						}
					}
				} else if (event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
					if (event.getEntity() instanceof Player) {
						Player shot = (Player) event.getEntity();
						if (currentGame.MURDERERS.contains(shot)) {
							currentGame.MURDERERS.remove(shot);
							if (currentGame.MURDERERS.isEmpty()) {
								currentGame.MURDERERS.add(shot);
								for (Player player : Bukkit.getOnlinePlayers()) {
									player.sendTitle(ChatColor.GREEN + "All murderers have been killed, innocents win", "", 10, 70, 20);
									Main.currentGame = null;
								}
							}
						} else {
							shot.setGameMode(GameMode.SPECTATOR);
							shot.getInventory().clear();
							shot.getWorld().playSound(shot.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 5);
							currentGame.INNOCENTS.remove(shot);
							
							currentGame.DETECTIVE.setGameMode(GameMode.SPECTATOR);
							shot.getInventory().clear();
							shot.getWorld().playSound(shot.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 5);
							currentGame.INNOCENTS.remove(shot);
							
							if (currentGame.INNOCENTS.isEmpty() && currentGame.DETECTIVE == null) {
								for (Player player : Bukkit.getOnlinePlayers()) {
									player.sendTitle(ChatColor.DARK_RED + "All innocents have been killed, murderers win", "", 10, 70, 20);
									Main.currentGame = null;
								}
							}
						}
					}
				}
			}
			Main.currentGame = currentGame;
		}
	}
	
	@EventHandler
	public void onPickupBow(EntityPickupItemEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getItem().getItemStack().getType() == Material.BOW) {
				if (Main.currentGame == null)
					return;
				
				if (Main.currentGame.DETECTIVE != null)
					return;
				
				if (Main.currentGame.MURDERERS.contains(player) || player.getInventory().contains(Material.BOW)) {
					event.setCancelled(true);
				}
				
				Main.currentGame.DETECTIVE = player;
				Main.currentGame.INNOCENTS.remove(player);
				
				for (Player player2 : Main.currentGame.INNOCENTS) {
					player2.sendTitle(ChatColor.GREEN + "A player has picked up the bow!", "You are protected", 10, 70, 20);
				}
				
				for (Player player2 : Main.currentGame.MURDERERS) {
					player2.sendTitle(ChatColor.DARK_RED + "A player has picked up the bow!", "You are in danger", 10, 70, 20);
				}
			}
		}
	}
}
