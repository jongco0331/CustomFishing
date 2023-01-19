package com.jonghyun.fishing.event;

import com.jonghyun.fishing.Fishing;
import com.jonghyun.fishing.Language;
import com.jonghyun.fishing.event.custom.PlayerJumpEvent;
import com.jonghyun.fishing.manager.FishManager;
import com.jonghyun.fishing.object.LengthFish;
import com.jonghyun.fishing.object.MiniGame;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.ChatBaseComponent;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onFishing(PlayerFishEvent e)
    {
        if(FishManager.getInstance().getMiniGameData().containsKey(e.getPlayer().getUniqueId()))
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
            FishManager.getInstance().getMiniGameData().put(e.getPlayer().getUniqueId(), new MiniGame());
            FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setSelectedLoc(new Random().nextInt(15));
            FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setHealth(100);
            FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setStart(true);
            FishManager.getInstance().getMiniGameData().get(e.getPlayer().getUniqueId()).setFirst(true);
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
                    FishManager.getInstance().getMiniGameData().remove(e.getPlayer().getUniqueId());
                    Bukkit.getServer().getScheduler().runTaskLater(Fishing.getPlugin(), () ->
                            e.getPlayer().sendTitle(Language.FISH_SUCCESS_TITLE, Language.FISH_SUCCESS_SUBTITLE, 0, 40, 0), 1);
                    LengthFish stack = FishManager.getInstance().getRandomLengthFish();
                    e.getPlayer().getInventory().addItem(stack.getStack());
                    e.getPlayer().sendMessage(Language.GET_FISH.replaceAll("<cm>", stack.getLength() + "")
                            .replaceAll("<fish>", stack.getStack().getItemMeta().getDisplayName()));

                }
            } else {
                if(miniGame.isFirst())
                {
                    FishManager.getInstance().getMiniGameData().remove(e.getPlayer().getUniqueId());
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
