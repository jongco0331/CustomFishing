package com.jonghyun.fishing.manager;

import com.jonghyun.fishing.loaders.ConfigLoader;
import com.jonghyun.fishing.loaders.CustomFishLoader;
import com.jonghyun.fishing.loaders.IDataLoader;
import com.jonghyun.fishing.loaders.RankLoader;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class LoadManager {

    private static LoadManager loadManager = null;

    private LoadManager() {}

    public static LoadManager getInstance()
    {
        if(loadManager == null)
            loadManager = new LoadManager();
        return loadManager;
    }

    @Getter
    List<IDataLoader> loaders = new ArrayList<>();

    public void load()
    {
        loaders.clear();
        loaders.add(new RankLoader());
        loaders.add(new CustomFishLoader());
        loaders.add(new ConfigLoader());

        for(IDataLoader loader : loaders)
        {
            loader.load();
        }
    }

    public void save()
    {
        for(IDataLoader loader : loaders)
        {
            loader.load();
        }
    }

}
