package co.schill.deathcount;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathCount extends JavaPlugin implements Listener{
	Logger log;
	public Map<Player, Integer> death_counts = new HashMap<Player, Integer>();
	
	public void onEnable(){
		log = this.getLogger();
		log.info("DeathCount is now enabled.");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable(){
		log.info("DeathCount is now disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("printdeaths")){
			if (args.length == 1){
				Player player = (Player)sender;
				OfflinePlayer target = getServer().getOfflinePlayer(args[0]);
				if (death_counts.containsKey(target)){
					player.sendMessage(target.getName() + "'s death count is " + death_counts.get(target));
					return true;
				}
				else{
					player.sendMessage(target.getName() + " has not died. Nyah!");
					return true;
				}
			}
			else{
				sender.sendMessage("Usage: /printdeaths [player]");
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player = event.getEntity();
		if (death_counts.containsKey(player)){
			death_counts.put(player, death_counts.get(player)+1);
		}
		else{
			death_counts.put(player, 1);
		}
		getServer().broadcastMessage(player.getName() + "'s death count is now " + death_counts.get(player));
	}
	
}

