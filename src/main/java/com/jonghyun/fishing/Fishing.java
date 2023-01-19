package com.jonghyun.fishing;

import com.jonghyun.fishing.commands.FishingBagCommand;
import com.jonghyun.fishing.commands.FishingCommand;
import com.jonghyun.fishing.event.Listener;
import com.jonghyun.fishing.event.custom.PlayerJumpEvent;
import com.jonghyun.fishing.guis.GuiFishingBag;
import com.jonghyun.fishing.guis.shop.GuiHavingItems;
import com.jonghyun.fishing.guis.shop.GuiHavingItems1;
import com.jonghyun.fishing.guis.shop.GuiShop;
import com.jonghyun.fishing.manager.FishingManager;
import com.jonghyun.fishing.manager.LoadManager;
import com.jonghyun.fishing.objects.MiniGame;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

public final class Fishing extends JavaPlugin {

    private static Fishing plugin;
    private static Economy econ = null;

    public static Fishing getPlugin() {
        return plugin;
    }

    public static Economy getEcon() {
        return econ;
    }

    @Override
    public void onEnable() {
        plugin = this;
        setupEconomy();
        getServer().getPluginManager().registerEvents(new Listener(), this);
        getServer().getPluginManager().registerEvents(new GuiFishingBag(), this);
        getServer().getPluginManager().registerEvents(new GuiShop(), this);
        getServer().getPluginManager().registerEvents(new GuiHavingItems(), this);
        getServer().getPluginManager().registerEvents(new GuiHavingItems1(), this);
        getServer().getPluginManager().registerEvents(new PlayerJumpEvent.CallJumpEvent(), this);
        LoadManager.getInstance().load();
        registerGlow();


        new FishingCommand();
        new FishingBagCommand();



        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers())
                {
                    HashMap<UUID, MiniGame> miniGameData = FishingManager.getInstance().getMiniGameData();
                    if(!miniGameData.containsKey(p.getUniqueId()))
                        continue;
                    if(!miniGameData.get(p.getUniqueId()).isStart())
                        continue;
                    MiniGame miniGame = miniGameData.get(p.getUniqueId());
                    if(miniGame.isRightMode()) {
                        miniGame.setCurrentLoc(miniGame.getCurrentLoc() + 1);
                        if(miniGame.getCurrentLoc() == 16)
                            miniGame.setRightMode(false);
                    } else {
                        miniGame.setCurrentLoc(miniGame.getCurrentLoc() - 1);
                        if(miniGame.getCurrentLoc() == 0)
                            miniGame.setRightMode(true);
                    }
                    String current = "■■■■■■■■■■■■■■■■■";
                    char[] a = current.toCharArray();
                    a[miniGame.getSelectedLoc()] = '□';
                    a[miniGame.getSelectedLoc()+1] = '□';
                    a[miniGame.getCurrentLoc()] = '◎';
                    current = new String(a);
                    current = current.replaceAll("◎", "§a■§f").replaceAll("□", "§e■§f");
                    String msg;
                    if(miniGame.isFirst())
                    {
                        msg = Language.FISHING_GUIDELINE;
                    } else {
                        msg = Language.FISHING_FISH_HEALTH.replaceAll("<health>", miniGame.getHealth() + "");
                    }
                    p.sendTitle(current, msg, 0, 20, 0);
                }
            }
        }, 2l, 2l);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onDisable() {
        LoadManager.getInstance().save();
    }

    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow(255);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
