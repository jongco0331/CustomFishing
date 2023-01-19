package com.jonghyun.fishing.objects.shop;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@Getter
public class EventObject {

    private ItemStack stack;
    private HashMap<EventClickType, String> event;

    public EventObject(ItemStack stack, HashMap<EventClickType, String> event) {
        this.stack = stack;
        this.event = event;
    }
}
