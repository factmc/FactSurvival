package net.factmc.FactSurvival;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import net.factmc.FactSurvival.commands.FactSurvivalCommand;
import net.factmc.FactSurvival.listeners.CompassController;

public class Main extends JavaPlugin implements Listener {
	
	public static JavaPlugin plugin;
	
    @Override
    public void onEnable() {
    	plugin = this;
    	
    	RecipeManager.loadRecipes();
    	
    	Bukkit.getPluginCommand("fsur").setExecutor(new FactSurvivalCommand());
    	registerEvents();

    }
    
    @Override
    public void onDisable() {
    	RecipeManager.unloadRecipes();
    	plugin = null;
    }
    
    public void registerEvents() {
    	
    	//Bukkit.getServer().getPluginManager().registerEvents(new RecipeManager(), plugin);
    	Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
    		@Override
    		public void run() {
    			for (Player player : Bukkit.getOnlinePlayers()) {
    				RecipeManager.checkRecipes(player);
    				CompassController.updateCompass(player);
    			}
    		}
    	}, 20L, 20L);
    	
    }
    
    public void registerCommands() {
    	
    }
    
    public static JavaPlugin getPlugin() {
        return plugin;
    }
    
}