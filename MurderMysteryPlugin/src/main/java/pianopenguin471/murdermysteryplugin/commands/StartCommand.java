package pianopenguin471.murdermysteryplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pianopenguin471.murdermysteryplugin.handlers.Game;
import pianopenguin471.murdermysteryplugin.Main;

public class StartCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Main.currentGame = new Game();
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.DARK_GREEN + "A game of Murder Mystery has started!"));
		Main.currentGame.DETECTIVE.sendTitle(ChatColor.DARK_BLUE + "Shoot the bow and kill the murderer", "", 10, 70, 20);
		Main.currentGame.INNOCENTS.forEach(player -> player.sendTitle(ChatColor.GREEN + "Find the murderer and stay alive!", "", 10, 70, 20));
		Main.currentGame.MURDERERS.forEach(player -> player.sendTitle(ChatColor.DARK_RED + "Kill All the players, but don't get found!", "", 10, 70, 20));
		return true;
	}
}
