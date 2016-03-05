package com.dankeroni.dankbot;

public class DankUtils{

    public static String joinString(String[] stringList){
        return String.join(" ", stringList);
    }

    public static String[] splitString(String string){
        try{
            return string.split("\\s+");
        }catch(Exception e){
            return null;
        }
    }

    public static int clamp(int var, int min, int max) {
        if(var >= max)
            return max;
        else if(var <= min)
            return min;
        else
            return var;
    }

}
