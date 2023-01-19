package com.jonghyun.fishing.guis;

import com.jonghyun.fishing.Sound;
import com.jonghyun.fishing.manager.FishingBagManager;
import com.jonghyun.fishing.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiFishingBag implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e)
    {
        if(e.getView().getTitle().contains("[ 낚시가방 ] "))
        {
            e.setCancelled(true);
            if(e.getRawSlot() > 53)
                return;
            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
                return;
            OfflinePlayer p = Bukkit.getOfflinePlayer(e.getView().getTitle().replaceAll("\\[ 낚시가방 \\] ", ""));
            if(!hasAvaliableSlot((Player) e.getWhoClicked()))
            {
                e.getWhoClicked().sendMessage("§c인벤토리를 한칸 비워주세요");
            }
            FishingBagManager bagManager = FishingBagManager.getInstance();
            e.getWhoClicked().getInventory().addItem(bagManager.bagMap.get(p.getUniqueId()).get(e.getRawSlot()));
            bagManager.bagMap.get(p.getUniqueId()).remove(e.getRawSlot());
            new GuiFishingBag((Player) e.getWhoClicked(), p.getPlayer());
        }
    }

    public GuiFishingBag() {}

    public GuiFishingBag(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "[ 낚시가방 ] " + p.getName());
        int i = 0;
        for(ItemStack fish : FishingBagManager.getInstance().bagMap.get(p.getUniqueId()))
        {
            inv.setItem(i, fish);
            i++;
        }
        p.openInventory(inv);
        SoundUtil.playSound(p, Sound.OPEN_BAG_GUI);
    }
    public GuiFishingBag(Player p, OfflinePlayer target)
    {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "[ 낚시가방 ] " + target.getName());
        int i = 0;
        for(ItemStack fish : FishingBagManager.getInstance().bagMap.get(target.getUniqueId()))
        {
            inv.setItem(i, fish);
            i++;
        }
        p.openInventory(inv);
        SoundUtil.playSound(p, Sound.OPEN_BAG_GUI);
    }

    public boolean hasAvaliableSlot(Player player){
        if(player.getInventory().firstEmpty() != -1) return true;
        return false;
    }

}
