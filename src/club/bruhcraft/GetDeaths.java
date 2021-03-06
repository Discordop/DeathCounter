package club.bruhcraft;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GetDeaths implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("deathcounter.getdeaths")) {
            if (args.length > 0) {
                String name = args[0];
                OfflinePlayer pl = Bukkit.getOfflinePlayer(name);
                if (DeathCounter.getPlugin().get_config().hasPlayer(pl.getUniqueId().toString())) {
                    String s = ChatColor.of("#d13bff") + "Deaths for " + name + ": " + ChatColor.of("#00ccff") + DeathCounter.getPlugin().get_config().getDeathsFor(pl.getUniqueId());
                    sender.sendMessage(s);
                } else {
                    sender.sendMessage(ChatColor.of("#d13bff") + "That player has not died!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Please supply a player!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that...");
        }
        return true;
    }
}
