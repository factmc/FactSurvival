package net.factmc.FactSurvival.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.factmc.FactSurvival.RecipeManager;
import net.factmc.FactSurvival.listeners.CompassController;

public class FactSurvivalCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fsur")) {
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
        			
        			ItemStack item = getItem(args[1]);
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
	
	private static ItemStack getItem(String item) {
		
		switch (item) {
		
		case "village_tracker":
			return RecipeManager.villageTracker;
		case "fortress_tracker":
			return RecipeManager.fortressTracker;
		case "city_tracker":
			return RecipeManager.cityTracker;
		default:
			return null;
		
		}
		
	}

}