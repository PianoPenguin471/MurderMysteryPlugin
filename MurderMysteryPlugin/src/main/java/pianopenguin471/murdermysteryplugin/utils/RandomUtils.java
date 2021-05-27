package pianopenguin471.murdermysteryplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Random;

public class RandomUtils {
	public static Random rnd = new Random();
	
	public static Player randomPlayer() {
		Object player = getRandomObject(Bukkit.getOnlinePlayers());
		return (Player) player;
	}
	
	public static Object getRandomObject(Collection from) {
		int i = rnd.nextInt(from.size());
		return from.toArray()[i];
	}
	
}
