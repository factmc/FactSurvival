package net.factmc.FactSurvival.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.StructureType;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.factmc.FactSurvival.Main;
import net.factmc.FactSurvival.RecipeManager;

public class CompassController {
	
	public static Map<Player, Map<StructureType, Location>> cachedLocations = new HashMap<Player, Map<StructureType, Location>>();
	public static Map<Player, Location> lastCompassLocations = new HashMap<Player, Location>();
	
	public static void updateCompass(Player player) {
		
		StructureType type = getStructureType(player.getWorld());
		ItemStack tracker = RecipeManager.getTracker(type);
		if (tracker == null) return;
		
		if (player.getWorld().getEnvironment() == Environment.NORMAL && player.getInventory().containsAtLeast(tracker, 1)) {
			
			//player.sendMessage("Village Location: " + village.getBlockX() + " " + village.getBlockZ());//DEBUG
			if (!lastCompassLocations.containsKey(player)) lastCompassLocations.put(player, player.getCompassTarget());
			Location location = getNearestStructure(player, type);
			if (!player.getCompassTarget().equals(location)) {
				player.setCompassTarget(location);
				//player.sendMessage("Compass Location: " + player.getCompassTarget().getBlockX() + " " + player.getCompassTarget().getBlockZ());//DEBUG
			}
			
		}
		
		else if (lastCompassLocations.containsKey(player)) {
			player.setCompassTarget(lastCompassLocations.get(player));
			/*Location l = lastCompassLocations.get(player);
			player.sendMessage("Set compass target back to " + String.valueOf(l));
			if (l != null)
				player.sendMessage("(" + lastCompassLocations.get(player).getX() + ", " + lastCompassLocations.get(player).getY() + ", " + lastCompassLocations.get(player).getZ());
			player.sendMessage("Compass Location: " + player.getCompassTarget().getBlockX() + " " + player.getCompassTarget().getBlockZ());*///DEBUG
			lastCompassLocations.remove(player);
		}
		
		if (player.getWorld().getEnvironment() != Environment.NORMAL &&
				(player.getInventory().getItemInMainHand().isSimilar(tracker) || player.getInventory().getItemInOffHand().isSimilar(tracker))) {
			
			Location location = getNearestStructure(player, type);
			createParticleCompass(player, location);
			
		}
		
	}
	
	public static void createParticleCompass(Player player, Location location) {
		
		double x = location.getX() - player.getLocation().getX();
		double z = location.getZ() - player.getLocation().getZ();
		Vector vec = new Vector(x, 0, z).normalize().multiply(1.5);
		
		Location start = player.getEyeLocation().clone();
		for (int i = 0; i < 3; i++) {
			start.add(vec);
			//player.sendMessage("Spawning Particle at " + start.getX() + "," + start.getY() + "," + start.getZ());//DEBUG
			player.spawnParticle(Particle.END_ROD, start, 1, 0, 0, 0, 0);
		}
		
	}
	
	
	
	public static Location getNearestStructure(Player player, StructureType type) {
		if (!cachedLocations.containsKey(player)) cachedLocations.put(player, new HashMap<StructureType, Location>());
		
		if (cachedLocations.get(player).containsKey(type)) return cachedLocations.get(player).get(type);
		//player.sendMessage("Finding Village...");//DEBUG
		Location location = player.getWorld().locateNearestStructure(player.getLocation(), type, radius(type), true);
		cachedLocations.get(player).put(type, location);
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
			@Override public void run() { cachedLocations.get(player).remove(type); }
		}, 600L);
		return location;
	}
	
	private static int radius(StructureType type) {
		if (type == StructureType.VILLAGE) return 200;
		else if (type == StructureType.NETHER_FORTRESS) return 100;
		else if (type == StructureType.END_CITY) return 150;
		else return -1;
	}
	
	
	private static StructureType getStructureType(World world) {
		if (world.getEnvironment() == Environment.NORMAL) return StructureType.VILLAGE;
		if (world.getEnvironment() == Environment.NETHER) return StructureType.NETHER_FORTRESS;
		if (world.getEnvironment() == Environment.THE_END) return StructureType.END_CITY;
		else return null;
	}
	
}