package club.bruhcraft;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Config {

    private static final String BASEDIR = "plugins/DeathTracker";
    private static final String CONFIGPATH = "/config.yml";
    private final YamlConfiguration config;
    private final File f = new File(BASEDIR);
    private final File ff = new File(BASEDIR + CONFIGPATH);

    public static <T> List<T> reverse(List<T> elements) {
        Collections.reverse(elements);
        return elements;
    }

    public Config() {
        if (!f.exists()) {
            f.mkdir();
        }
        if (!f.exists()) {
            try {
                ff.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(ff);

    }
    public Map<OfflinePlayer, Integer> collect() {
        ConfigurationSection section = config.getConfigurationSection("players");
        Map<OfflinePlayer, Integer> map = new HashMap<>();
        if (section != null) {
            section.getValues(false).forEach((k, v) -> {
                map.put(Bukkit.getOfflinePlayer(UUID.fromString(k)), (int) v);
            });
        }
        return map;
    }
    public void add(PlayerDeathEvent event) {
        int count = 1;
        if (config.isSet("players." + event.getEntity().getUniqueId())) {
            count = config.getInt("players." + event.getEntity().getUniqueId()) + 1;
        }
        config.set("players." + event.getEntity().getUniqueId(), count);
        ForkJoinPool.commonPool().submit(() -> {
            try {
                config.save(ff);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public boolean hasPlayer(String uuid) {
        ConfigurationSection sec = config.getConfigurationSection("players");
        return sec != null && sec.contains(uuid);
    }
    public int getDeathsFor(UUID player) {
        return config.getInt("players." + player.toString());
    }
}
