package com.jonghyun.fishing.event;

import com.jonghyun.fishing.event.custom.PlayerJumpEvent;
import com.jonghyun.fishing.manager.FishManager;
import com.jonghyun.fishing.object.MiniGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onFishing(PlayerFishEvent e)
    {
        if(e.getState() == PlayerFishEvent.State.CAUGHT_FISH)
        {
            e.setExpToDrop(0);
            e.getCaught().remove();
            FishManager.getInstance().getMiniGameData().put(e.getPlayer().getUniqueId(), new MiniGame());
            FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setSelectedLoc(8);
            FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setHealth(70);
            FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setStart(true);
            FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setRightMode(true);
            FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setCurrentLoc(0);

        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJump(PlayerJumpEvent e)
    {
        MiniGame miniGame = FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId());
        if(miniGame == null)
            return;
        if(miniGame.isStart())
            e.setCancelled(true);
    }


}
