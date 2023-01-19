package com.jonghyun.fishing.manager;

import com.jonghyun.fishing.Sound;
import com.jonghyun.fishing.guis.shop.GuiHavingItems;
import com.jonghyun.fishing.guis.shop.GuiHavingItems1;
import com.jonghyun.fishing.objects.fish.CustomFish;
import com.jonghyun.fishing.objects.fish.ExchangeItem;
import com.jonghyun.fishing.objects.fish.SellPrice;
import com.jonghyun.fishing.objects.shop.FishShop;
import com.jonghyun.fishing.utils.SoundUtil;
import com.jonghyun.fishing.utils.VaultUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class FishingShopManager {

    private static FishingShopManager fishShopManager = null;

    private FishingShopManager() {}

    public static FishingShopManager getInstance()
    {
        if(fishShopManager == null)
            fishShopManager = new FishingShopManager();
        return fishShopManager;
    }

    @Getter
    private HashMap<String, FishShop> fishingShops = new HashMap<>();

    public void performEvent(Player p, String event)
    {
        CustomFish customFish = FishingManager.getInstance().getCustomFishes().get(event.split(" ")[1]);
        List<ItemStack> stacks = getAllItemsInPlayer(p, customFish);
        if(event.contains("SI "))
        {
            new GuiHavingItems(p, customFish.getId(), stacks);
        } else
        if(event.contains("SAI "))
        {
            for(ItemStack a : stacks)
            {
                int amount = a.getAmount();
                double length = FishingManager.getInstance().getLengthOfFish(a);
                for(ExchangeItem e : customFish.getExchangeItems())
                {
                    String[] split = e.getRange().split("~");
                    double min = Double.parseDouble(split[0]);
                    double max = Double.parseDouble(split[1]);
                    if(FishingManager.getInstance().isBetween(length, min, max))
                    {
                        for(int i = 0; i < amount; i++)
                        {
                            if(p.getInventory().firstEmpty() != -1) {
                                p.getInventory().addItem(e.getExchange());
                                FishingShopManager.getInstance().reduceItemInPlayer(p, a);
                                a.setAmount(a.getAmount() - 1);
                                SoundUtil.playSound(p, Sound.SELL_ITEM);
                            } else
                            p.sendMessage("§c인벤토리를 한칸 비워주세요");
                        }
                    }
                }
            }
            SoundUtil.playSound(p, Sound.SELL_ITEM);
        } else
        if(event.contains("SM "))
        {
            new GuiHavingItems1(p, customFish.getId(), stacks);
        } else
        if(event.contains("SAM "))
        {
            for(ItemStack a : stacks)
            {
                int amount = a.getAmount();
                double length = FishingManager.getInstance().getLengthOfFish(a);
                for(SellPrice e : customFish.getPrice())
                {
                    String[] split = e.getRange().split("~");
                    double min = Double.parseDouble(split[0]);
                    double max = Double.parseDouble(split[1]);
                    if(FishingManager.getInstance().isBetween(length, min, max))
                    {
                        for(int i = 0; i < amount; i++)
                        {
                            if(p.getInventory().firstEmpty() != -1) {
                                VaultUtil.addMoney(p, e.getPrice());
                                FishingShopManager.getInstance().reduceItemInPlayer(p, a);
                                a.setAmount(a.getAmount() - 1);
                                SoundUtil.playSound(p, Sound.SELL_ITEM);
                            } else
                            p.sendMessage("§c인벤토리를 한칸 비워주세요");
                        }
                    }
                }
            }
            SoundUtil.playSound(p, Sound.SELL_ITEM);
        }
    }

    public List<ItemStack> getAllItemsInPlayer(Player p, CustomFish customFish)
    {
        List<ItemStack> item = new ArrayList<>();
        for(ItemStack stack : p.getInventory().getContents())
        {
            if(stack == null) continue;
            if(stack.getType() == Material.AIR) continue;
            if(!stack.hasItemMeta()) continue;
            if(!stack.getItemMeta().hasDisplayName()) continue;
            if(stack.getItemMeta().getDisplayName().equals(customFish.getFish().getItemMeta().getDisplayName()))
            {
                item.add(stack);
            }
        }
        for(ItemStack stack : FishingBagManager.getInstance().bagMap.get(p.getUniqueId()))
        {
            if(stack == null) continue;
            if(stack.getItemMeta().getDisplayName().equals(customFish.getFish().getItemMeta().getDisplayName()))
            {
                item.add(stack);
            }
        }
        return item;
    }

    public void reduceItemInPlayer(Player p, ItemStack fish)
    {
        for(ItemStack stack : p.getInventory().getContents()) {
            if (stack == null) continue;
            if (stack.getType() == Material.AIR) continue;
            if (!stack.hasItemMeta()) continue;
            if (!stack.getItemMeta().hasDisplayName()) continue;
            if (stack.isSimilar(fish)) {
                stack.setAmount(stack.getAmount() - 1);
                return;
            }
        }
        FishingBagManager bagManager = FishingBagManager.getInstance();
        int i = 0;
        for(ItemStack stack : bagManager.bagMap.get(p.getUniqueId())) {
            if (stack == null) { i++; continue; }
            if(stack.isSimilar(fish))
            {
                stack.setAmount(stack.getAmount() - 1);
                if(stack.getAmount() == 0)
                {
                    bagManager.bagMap.remove(i);
                }
                return;
            }
            i++;
        }
    }



}
