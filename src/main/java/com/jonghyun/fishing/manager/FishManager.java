package com.jonghyun.fishing.manager;

import com.jonghyun.fishing.object.CustomFish;
import com.jonghyun.fishing.object.MiniGame;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class FishManager {

    private static FishManager fishManager = null;

    private FishManager() {}

    public static FishManager getInstance()
    {
        if(fishManager == null)
            fishManager = new FishManager();
        return fishManager;
    }
    @Getter @Setter
    private HashMap<String, CustomFish> customFishes = new HashMap<>();
    @Getter
    private HashMap<String, List<CustomFish>> sortedRankFishes = new HashMap<>();
    @Getter
    private HashMap<UUID, MiniGame> miniGameData = new HashMap<>();

}
