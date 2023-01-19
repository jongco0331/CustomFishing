package com.jonghyun.fishing.objects.fish;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class LengthFish {

    private ItemStack stack;
    private double length;

    public LengthFish(ItemStack stack, double length) {
        this.stack = stack;
        this.length = length;
    }
}
