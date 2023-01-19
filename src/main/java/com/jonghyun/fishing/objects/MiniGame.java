package com.jonghyun.fishing.objects;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MiniGame {

    private boolean isStart;
    private int currentLoc;
    private int selectedLoc;
    private int health;
    private boolean isRightMode;
    private boolean isFirst;

}
