package pianopenguin471.murdermysteryplugin;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pianopenguin471.murdermysteryplugin.commands.StartCommand;
import pianopenguin471.murdermysteryplugin.handlers.Game;
import pianopenguin471.murdermysteryplugin.listeners.EventListener;

public final class Main extends JavaPlugin {
	public static Game currentGame = null;
	@Override
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new EventListener(), this);
		this.getCommand("start").setExecutor(new StartCommand());
	}
	@Override
	public void onDisable() {
	}
}
