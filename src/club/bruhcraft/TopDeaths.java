package club.bruhcraft;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TopDeaths implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("deathcounter.topdeaths")) {
            int limit = 3;
            if (args.length > 0) {
                limit = Integer.parseInt(args[0]);
            }
            List<Map.Entry<OfflinePlayer, Integer>> entries = Config.reverse(DeathCounter.getPlugin().get_config().collect().entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(limit).collect(Collectors.toList()));
            StringBuilder sb = new StringBuilder();
            AtomicInteger elementNumber = new AtomicInteger(1);
            if (entries.isEmpty()) sb.append(ChatColor.RED).append("No deaths!");
            else {
                sb.append(ChatColor.of("#00ddff")).append("---------------------------------\n");
                entries.forEach((e) -> {
                    sb.append(ChatColor.of("#00ffb7")).append("#").append(elementNumber.get()).append(" ").append(ChatColor.of("#d13bff")).append(e.getKey().getName()).append(": ").append(ChatColor.RESET).append(ChatColor.of("#00ccff")).append(e.getValue()).append(" deaths\n");
                    elementNumber.getAndIncrement();
                });
                sb.append(ChatColor.of("#00ddff")).append("---------------------------------");
            }
            sender.sendMessage(sb.toString());
        } else {
            sender.sendMessage(ChatColor.RED + "You can't do that!");
        }
        return true;
    }
}
