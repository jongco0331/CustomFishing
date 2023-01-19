package com.jonghyun.fishing.event;

import com.jonghyun.fishing.Fishing;
import com.jonghyun.fishing.Language;
import com.jonghyun.fishing.event.custom.PlayerJumpEvent;
import com.jonghyun.fishing.manager.FishingManager;
import com.jonghyun.fishing.manager.FishingBagManager;
import com.jonghyun.fishing.objects.fish.LengthFish;
import com.jonghyun.fishing.objects.MiniGame;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Random;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        if(!FishingBagManager.getInstance().bagMap.containsKey(e.getPlayer().getUniqueId()))
            FishingBagManager.getInstance().bagMap.put(e.getPlayer().getUniqueId(), new ArrayList<>());
    }

    @EventHandler
    public void onFishing(PlayerFishEvent e)
    {
        if(FishingManager.getInstance().getMiniGameData().containsKey(e.getPlayer().getUniqueId()))
            e.setCancelled(true);
        if(e.getState() == PlayerFishEvent.State.CAUGHT_FISH)
        {
            if(!e.getPlayer().isOnGround()) {
                e.getPlayer().sendMessage("§c낚시는 땅에서 해주세요");
                e.setCancelled(true);
                return;
            }
            e.setExpToDrop(0);
            e.getCaught().remove();
            FishingManager.getInstance().getMiniGameData().put(e.getPlayer().getUniqueId(), new MiniGame());
            FishingManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setSelectedLoc(new Random().nextInt(15));
            FishingManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setHealth(100);
            FishingManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setStart(true);
            FishingManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setFirst(true);
            FishingManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setRightMode(true);
            FishingManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setCurrentLoc(0);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJump(PlayerJumpEvent e)
    {
        MiniGame miniGame = FishingManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId());
        if(miniGame == null)
            return;
        if(miniGame.isStart()) {
            e.setCancelled(true);
            if(miniGame.getCurrentLoc() == miniGame.getSelectedLoc() || miniGame.getCurrentLoc() == miniGame.getSelectedLoc() + 1)
            {
                miniGame.setFirst(false);
                miniGame.setHealth(miniGame.getHealth() - 20);
                miniGame.setSelectedLoc(new Random().nextInt(15));
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                if(miniGame.getHealth() == 0)
                {
                    FishingManager.getInstance().getMiniGameData().remove(e.getPlayer().getUniqueId());
                    Bukkit.getServer().getScheduler().runTaskLater(Fishing.getPlugin(), () ->
                            e.getPlayer().sendTitle(Language.FISH_SUCCESS_TITLE, Language.FISH_SUCCESS_SUBTITLE, 0, 40, 0), 1);
                    LengthFish stack = FishingManager.getInstance().getRandomLengthFish();
                    FishingBagManager.getInstance().bagMap.get(e.getPlayer().getUniqueId()).add(stack.getStack());
                    e.getPlayer().sendMessage(Language.GET_FISH.replaceAll("<cm>", stack.getLength() + "")
                            .replaceAll("<fish>", stack.getStack().getItemMeta().getDisplayName()));

                }
            } else {
                if(miniGame.isFirst())
                {
                    FishingManager.getInstance().getMiniGameData().remove(e.getPlayer().getUniqueId());
                    Bukkit.getServer().getScheduler().runTaskLater(Fishing.getPlugin(), () ->
                            e.getPlayer().sendTitle("§f[ §c낚시 실패! §f]", "§7물고기가 저멀리 도망가버렸습니다", 0, 40, 0), 1);
                } else {
                    miniGame.setFirst(false);
                    if(miniGame.getHealth() < 100)
                        miniGame.setHealth(miniGame.getHealth() + 20);
                    miniGame.setSelectedLoc(new Random().nextInt(15));
                }
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            }
        }
    }


}
