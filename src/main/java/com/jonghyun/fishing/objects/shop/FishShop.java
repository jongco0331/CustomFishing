package com.jonghyun.fishing.objects.shop;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class FishShop {

    private String[] contents;
    private HashMap<String, EventObject> componentsMap;
    private String npc;

    public FishShop(String[] contents, HashMap<String, EventObject> componentsMap, String npc) {
        this.contents = contents;
        this.componentsMap = componentsMap;
        this.npc = npc;
    }
}
