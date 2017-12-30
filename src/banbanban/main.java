package banbanban;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener{
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(this,this);
	}
	
	public void onDisable(){
		
	}

	private  ArrayList<String> playerlist = new ArrayList<String>();
	
	public boolean onCommand(CommandSender sender,Command cmd,String cmdlable,String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "請使用GM身分輸入");
			return true;
		}
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("sban")){
			if(sender.hasPermission("sban")){
				if(args.length == 0){
					player.sendMessage(ChatColor.GOLD + "使用方法: /sban <player>");
					return true;
				}
				if(args.length == 1){
					if("help".equalsIgnoreCase(args[0])){
						player.sendMessage(ChatColor.GOLD + "使用方法: /sban <player>");
						return true;
					}
					playerlist.add(args[0]);
					player.sendMessage(ChatColor.GOLD + "【曙光插件】"+ ChatColor.RED + args[0] + " 已被強制剔除");
					this.getServer().broadcast(ChatColor.GOLD + "【曙光插件】管理員 " + ChatColor.RED + player.getName() 
												+ ChatColor.GOLD + " 將 " + ChatColor.RED +args[0]+ChatColor.GOLD +" 剔除","sban");
					return true;
				}
			}else {
				player.sendMessage(ChatColor.RED + "你沒有此指令的權限！");
			}
		}
		
		
		
		if(cmd.getName().equalsIgnoreCase("sunban")){
			if(sender.hasPermission("sunban")){
				if(args.length == 0){
					player.sendMessage(ChatColor.GOLD + "使用方法: /sunban <player>");
					return true;
				}
				if(args.length == 1){
					if("help".equalsIgnoreCase(args[0])){
						player.sendMessage(ChatColor.GOLD + "使用方法: /sunban <player>");
						return true;
					}
					if(playerlist.contains(args[0]))
					playerlist.remove(args[0]);
					player.sendMessage(ChatColor.GOLD + "【曙光插件】"+ ChatColor.RED + args[0] + " 已解鎖強制剔除");
					return true;
				}
			}else{
				player.sendMessage(ChatColor.RED + "你沒有此指令的權限！");
			}
		}
		return true;
	}
	
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e){
		String p = e.getPlayer().getName();
		if (!p.matches("^[a-zA-Z_0-9§]+$")){
			e.disallow(Result.KICK_BANNED ,ChatColor.GREEN  + "本服帳號只允許 " + ChatColor.RED + "英文及數字");
			this.getServer().broadcast(ChatColor.GOLD + "【曙光插件】 已將" + ChatColor.RED + p 
			+ChatColor.GOLD +" 剔除","sban");
		}
		
		if(playerlist.contains(e.getPlayer().getName())){
			e.disallow(Result.KICK_BANNED, ChatColor.RED + "你已被伺服器驅離！" );
		}
	}
}
