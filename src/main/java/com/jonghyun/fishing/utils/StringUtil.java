package com.jonghyun.fishing.utils;

import java.util.List;

public class StringUtil {

    public static String colorize(String a)
    {
        if(a != null)
            return a.replaceAll("&", "§");
        return null;
    }

    public static List<String> colorize(List<String> a)
    {
        if(a != null)
        {
            final int[] i = {0};
            a.forEach(s -> {
                a.set(i[0], colorize(s));
                i[0] +=1;
            });
            return a;
        }
        return null;
    }

}
