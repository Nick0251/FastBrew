package me.eqxdev.fastbrew;

import me.eqxdev.fastbrew.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by eqxDev on 1/14/2017.
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigManager.load(this, "config.yml");
        getServer().getPluginManager().registerEvents(new SmeltListeners(this, ConfigManager.get("config.yml").getInt("furnaceTime"),ConfigManager.get("config.yml").getInt("brewTime")), this);
    }
}
