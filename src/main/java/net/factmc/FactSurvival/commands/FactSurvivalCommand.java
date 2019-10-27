package net.factmc.FactSurvival.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.factmc.FactCore.CoreUtils;
import net.factmc.FactSurvival.RecipeManager;
import net.factmc.FactSurvival.listeners.CompassController;

public class FactSurvivalCommand implements CommandExecutor, TabExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fsur")) {
        	
        	if (!sender.hasPermission("factsurvival.admin")) return false;
        	
        	if (args.length > 0) {
        		
        		if (args[0].equalsIgnoreCase("reload")) {
        			
        			RecipeManager.unloadRecipes();
                	CompassController.cachedLocations.clear();
                	RecipeManager.loadRecipes();
                	
                	sender.sendMessage(ChatColor.GREEN + "FactSurvival has been reloaded");
                	return true;
        			
        		}
        		
        		else if (args[0].equalsIgnoreCase("give")) {
        			
        			if (!(sender instanceof Player)) {
        				sender.sendMessage(ChatColor.DARK_RED + "Only players can use that");
        				return false;
        			}
        			
        			if (args.length < 2) {
        				sender.sendMessage(ChatColor.RED + "Usage: /" + label + " give <item>");
        				return false;
        			}
        			
        			ItemStack item = getItems().get(args[1]);
        			if (item == null) {
        				sender.sendMessage(ChatColor.RED + args[1] + " is not known");
        				return false;
        			}
        			
        			((Player) sender).getInventory().addItem(item);
        			sender.sendMessage(ChatColor.GREEN + "You have been given a " + item.getItemMeta().getDisplayName());
        			return true;
        			
        		}
        		
        	}
        	
        	sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <reload|give>");
        	return false;
        	
        }
        
		return false;   
    }
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (command.getName().equalsIgnoreCase("fsur")) {
			
			if (args.length < 2) return CoreUtils.filter(CoreUtils.toList("reload", "give"), args[0]);
			
			else if (args[0].equalsIgnoreCase("give")) {
				
				if (args.length == 2) return CoreUtils.filter(getItemNames(), args[1]);
				
			}
			
			return CoreUtils.toList();
			
		}
		
		return null;
	}
	
	private static Map<String, ItemStack> getItems() {
		Map<String, ItemStack> map = new HashMap<String, ItemStack>();
		map.put("village_tracker", RecipeManager.villageTracker);
		map.put("fortress_tracker", RecipeManager.fortressTracker);
		map.put("city_tracker", RecipeManager.cityTracker);
		return map;
	}
	
	private static List<String> getItemNames() {
		List<String> list = new ArrayList<String>();
		for (String key : getItems().keySet()) {
			list.add(key);
		}
		return list;
	}

}