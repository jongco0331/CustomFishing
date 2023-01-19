package com.jonghyun.fishing.guis.shop;

import com.jonghyun.fishing.Sound;
import com.jonghyun.fishing.manager.FishingManager;
import com.jonghyun.fishing.manager.FishingShopManager;
import com.jonghyun.fishing.objects.fish.CustomFish;
import com.jonghyun.fishing.objects.fish.ExchangeItem;
import com.jonghyun.fishing.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiHavingItems implements Listener {

    public GuiHavingItems(Player p, String id, List<ItemStack> stacks)
    {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "[ 낚시판매 ] " + id);
        for(int i = 0; i < stacks.size(); i++)
        {
            inv.setItem(i, stacks.get(i));
        }
        p.openInventory(inv);
    }

    public GuiHavingItems() {}

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("[ 낚시판매 ] ")) {
            e.setCancelled(true);
            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
                return;
            FishingManager fishingManager = FishingManager.getInstance();
            CustomFish fish = fishingManager.getCustomFishes().get(e.getView().getTitle().replaceAll("\\[ 낚시판매 \\] ", ""));
            double length = FishingManager.getInstance().getLengthOfFish(e.getCurrentItem());
            for(ExchangeItem event : fish.getExchangeItems())
            {
                String[] split = event.getRange().split("~");
                double min = Double.parseDouble(split[0]);
                double max = Double.parseDouble(split[1]);
                if(fishingManager.isBetween(length, min, max))
                {
                    if(e.getWhoClicked().getInventory().firstEmpty() != -1) {
                        e.getWhoClicked().getInventory().addItem(event.getExchange());
                        FishingShopManager.getInstance().reduceItemInPlayer((Player) e.getWhoClicked(), e.getCurrentItem());
                        e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() - 1);
                        SoundUtil.playSound((Player) e.getWhoClicked(), Sound.SELL_ITEM);
                        return;
                    }
                    e.getWhoClicked().sendMessage("§c인벤토리를 한칸 비워주세요");
                    return;
                }
            }
        }
    }
}
