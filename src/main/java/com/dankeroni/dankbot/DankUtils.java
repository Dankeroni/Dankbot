package com.dankeroni.dankbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class DankUtils{

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

    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
