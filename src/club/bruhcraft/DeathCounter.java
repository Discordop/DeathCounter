package club.bruhcraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathCounter extends JavaPlugin {
    private static DeathCounter plugin = null;
    public static DeathCounter getPlugin() {
        return plugin;
    }
    private Config cfg = new Config();
    @Override
    public void onEnable() {
        plugin = this;
        this.getCommand("topdeaths").setExecutor(new TopDeaths());
        this.getCommand("getdeaths").setExecutor(new GetDeaths());
        Bukkit.getPluginManager().registerEvents(new DeathHandler(), this);
    }
    public Config get_config() {
        return cfg;
    }
}
