package com.jonghyun.fishing.objects.shop;

public enum EventClickType {

    LEFT, RIGHT, SHIFT_LEFT, SHIFT_RIGHT;

    public static EventClickType value(String type)
    {
        if(type.equalsIgnoreCase("left"))
            return LEFT;
        else if(type.equalsIgnoreCase("right"))
            return RIGHT;
        else if(type.equalsIgnoreCase("shift-left"))
            return SHIFT_LEFT;
        else if(type.equalsIgnoreCase("shift-right"))
            return SHIFT_RIGHT;
        return null;
    }


}
