package net.factmc.FactSurvival;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.StructureType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class RecipeManager {
	
	public static ItemStack villageTracker, fortressTracker, cityTracker = null;
	public static ShapedRecipe villageTrackerRecipe, fortressTrackerRecipe, cityTrackerRecipe, saddleRecipe = null;
	
	public static void loadRecipes() {
		
		loadVillageTrackerRecipe();
		loadFortressTrackerRecipe();
		loadCityTrackerRecipe();
		
		loadSaddleRecipe();
		
	}
	
	public static void unloadRecipes() {
		
		/*Iterator<Recipe> iter = Bukkit.recipeIterator();
		while (iter.hasNext()) {
			Recipe recipe = iter.next();
			if (recipe.equals(villageTrackerRecipe)) {
				iter.remove();
				break;
			}
		}*/
		
		villageTrackerRecipe = null;
		fortressTrackerRecipe = null;
		cityTrackerRecipe = null;
		saddleRecipe = null;
		villageTracker = null;
		fortressTracker = null;
		cityTracker = null;
		
	}
	
	public static void checkRecipes(Player player) {
		
		PlayerInventory inv = player.getInventory();
		if (inv.contains(Material.EMERALD)) player.discoverRecipe(villageTrackerRecipe.getKey());
		if (inv.contains(Material.SOUL_SAND)) player.discoverRecipe(fortressTrackerRecipe.getKey());
		if (inv.contains(Material.END_ROD)) player.discoverRecipe(cityTrackerRecipe.getKey());
		if (inv.contains(Material.LEATHER)) player.discoverRecipe(saddleRecipe.getKey());
		
	}
	
	
	
	private static ShapedRecipe loadVillageTrackerRecipe() {
		
		if (villageTracker == null) {
			villageTracker = new ItemStack(Material.COMPASS);
			ItemMeta meta = villageTracker.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Village Tracker");
			villageTracker.setItemMeta(meta);
		}
		
		if (villageTrackerRecipe == null) {
			villageTrackerRecipe = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "village_tracker"), villageTracker);
			villageTrackerRecipe.shape("aea", "ece", "aea").setIngredient('a', Material.AIR)
					.setIngredient('e', Material.EMERALD).setIngredient('c', Material.COMPASS);
		}
		
		try {
			Bukkit.addRecipe(villageTrackerRecipe);
		} catch (IllegalStateException e) {
			Main.getPlugin().getLogger().warning(e.getMessage());
		}
		return villageTrackerRecipe;
		
	}
	
	private static ShapedRecipe loadFortressTrackerRecipe() {
		
		if (fortressTracker == null) {
			fortressTracker = new ItemStack(Material.COMPASS);
			ItemMeta meta = fortressTracker.getItemMeta();
			meta.setDisplayName(ChatColor.AQUA + "Nether Fortress Tracker");
			meta.addEnchant(Enchantment.DURABILITY, 0, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta = addLore(meta, "", ChatColor.GRAY + "Hold in your main hand", ChatColor.GRAY + "or off hand to use");
			fortressTracker.setItemMeta(meta);
		}
		
		if (fortressTrackerRecipe == null) {
			fortressTrackerRecipe = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "fortress_tracker"), fortressTracker);
			fortressTrackerRecipe.shape("asa", "scs", "asa").setIngredient('a', Material.AIR)
					.setIngredient('s', Material.SOUL_SAND).setIngredient('c', Material.COMPASS);
		}
		
		try {
			Bukkit.addRecipe(fortressTrackerRecipe);
		} catch (IllegalStateException e) {
			Main.getPlugin().getLogger().warning(e.getMessage());
		}
		return fortressTrackerRecipe;
		
	}
	
	private static ShapedRecipe loadCityTrackerRecipe() {
		
		if (cityTracker == null) {
			cityTracker = new ItemStack(Material.COMPASS);
			ItemMeta meta = cityTracker.getItemMeta();
			meta.setDisplayName(ChatColor.AQUA + "End City Tracker");
			meta.addEnchant(Enchantment.DURABILITY, 0, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta = addLore(meta, "", ChatColor.GRAY + "Hold in your main hand", ChatColor.GRAY + "or off hand to use");
			cityTracker.setItemMeta(meta);
		}
		
		if (cityTrackerRecipe == null) {
			cityTrackerRecipe = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "city_tracker"), cityTracker);
			cityTrackerRecipe.shape("ara", "rcr", "ara").setIngredient('a', Material.AIR)
					.setIngredient('r', Material.END_ROD).setIngredient('c', Material.COMPASS);
		}
		
		try {
			Bukkit.addRecipe(cityTrackerRecipe);
		} catch (IllegalStateException e) {
			Main.getPlugin().getLogger().warning(e.getMessage());
		}
		return cityTrackerRecipe;
		
	}
	
	private static ShapedRecipe loadSaddleRecipe() {
		
		if (saddleRecipe == null) {
			saddleRecipe = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), "saddle"), new ItemStack(Material.SADDLE, 1));
			saddleRecipe.shape("sls", "rlr", "tlt").setIngredient('s', Material.STICK)
					.setIngredient('l', Material.LEATHER).setIngredient('r', Material.STRING).setIngredient('t', Material.TRIPWIRE_HOOK);
		}
		
		try {
			Bukkit.addRecipe(saddleRecipe);
		} catch (IllegalStateException e) {
			Main.getPlugin().getLogger().warning(e.getMessage());
		}
		return saddleRecipe;
		
	}
	
	public static ItemStack getTracker(StructureType type) {
		if (type == StructureType.VILLAGE) return villageTracker;
		else if (type == StructureType.NETHER_FORTRESS) return fortressTracker;
		else if (type == StructureType.END_CITY) return cityTracker;
		else return null;
	}
	
	
	
	public static ItemMeta addLore(ItemMeta meta, String... lines) {
		
		List<String> lore = new ArrayList<String>();
		for (String line : lines) {
			lore.add(line);
		}
		meta.setLore(lore);
		return meta;
		
	}
	
	/*
	public static void registerCustomEnchants() {
		
		try {
			if (Enchantment.isAcceptingRegistrations()) Main.getPlugin().getLogger().info("Server accepting new enchantments");
			Field acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
			boolean isAccessible = acceptingNew.isAccessible();
			acceptingNew.setAccessible(true);
			acceptingNew.set(null, true);
			if (Enchantment.isAcceptingRegistrations()) Main.getPlugin().getLogger().info("Server accepting new enchantments");
			Enchantment.registerEnchantment(FLIGHT);
			Main.getPlugin().getLogger().info("Registered flight enchantment");
			Enchantment.stopAcceptingRegistrations();
			if (!Enchantment.isAcceptingRegistrations()) Main.getPlugin().getLogger().info("Server not accepting new enchantments");
			acceptingNew.setAccessible(isAccessible);
		} catch (IllegalStateException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}*/
	
}