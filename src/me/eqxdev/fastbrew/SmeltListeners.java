package me.eqxdev.fastbrew;

import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eqxDev on 1/14/2017.
 */
public class SmeltListeners implements Listener {

    private List<BrewingStand> stands = new ArrayList<>();
    private List<Furnace> furnaces = new ArrayList<>();


    public SmeltListeners(JavaPlugin main, int furnaceTime, int brewTime) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (BrewingStand stand : stands) {
                    if (stand.getLocation().getChunk().isLoaded()) {
                        if (stand.getBrewingTime() > 1) {
                            stand.setBrewingTime(Math.max(1, stand.getBrewingTime() - brewTime));
                        }
                    }
                }
                for (Furnace furnace : furnaces) {
                    if (furnace.getInventory().getItem(0) != null) {
                        furnace.setCookTime((short) (furnace.getCookTime() + furnaceTime));
                        furnace.setBurnTime((short) (furnace.getBurnTime() + furnaceTime));
                    } else {
                        furnace.setCookTime((short) 0);
                        furnace.setBurnTime((short) 0);
                    }
                }
            }
        }.runTaskTimer(main, 2L, 2L);
    }

    @EventHandler
    public void onFurnaceSmelt(FurnaceSmeltEvent e) {
        Furnace furnace = (Furnace) e.getFurnace().getState();
        if (!(furnaces.contains(furnace))) {
            furnaces.add(furnace);
        }
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent e) {
        Furnace furnace = (Furnace) e.getFurnace().getState();
        if (!(furnaces.contains(furnace))) {
            furnaces.add(furnace);
        }
    }

    @EventHandler
    public void brew(BrewEvent e) {
        BrewingStand brewingStand = (BrewingStand) e.getBlock().getState();
        if (!(stands.contains(brewingStand))) {
            stands.add(brewingStand);
        }
    }

    @EventHandler
    public void onClickBlock(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.BREWING_STAND) {
                BrewingStand brewingStand = (BrewingStand) e.getClickedBlock().getState();
                if (!(stands.contains(brewingStand))) {
                    stands.add(brewingStand);
                }
            }
            if (e.getClickedBlock().getType() == Material.FURNACE || e.getClickedBlock().getType() == Material.BURNING_FURNACE) {
                Furnace furnace = (Furnace) e.getClickedBlock().getState();
                if (!(furnaces.contains(furnace))) {
                    furnaces.add(furnace);
                }
            }
        }
    }

}
