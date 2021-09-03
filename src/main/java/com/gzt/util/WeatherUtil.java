package com.gzt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WeatherUtil {
    public static String getWeather(String cityOrArea){
        String updateAssetUrl;
        StringBuffer strBuf = new StringBuffer();
        try{
            String city = URLEncoder.encode(cityOrArea, "utf-8");
            updateAssetUrl = "https://restapi.amap.com/v3/weather/weatherInfo?extensions=base&key=40b42534bde2799c566fe84b3333f938&city="+city;
            URL url = new URL(updateAssetUrl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return strBuf.toString();
    }
}
