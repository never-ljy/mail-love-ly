package com.gzt.controller;

import com.gzt.model.Msg;
import com.gzt.redis.RedisServiceImpl;
import com.gzt.service.MsgService;
import com.gzt.util.WeatherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("msg")
public class MsgController {
    @Autowired
    private RedisServiceImpl redisServiceImpl;
    @Autowired
    private MsgService msgService;

    @RequestMapping("queryList")
    public Map queryList(Integer start,Integer pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<String, Object> msgMap = msgService.queryList(start,pageSize);
            map.put("code",200);
            map.put("msgList",msgMap.get("msgList"));
            map.put("total",msgMap.get("total"));
        }catch (Exception e){
            map.put("code",500);
        }
        return map;
    }
    @RequestMapping("del")
    public Map del(Msg msg){
        msgService.del(msg);
        return null;
    }

    @RequestMapping("upd")
    public Map upd(Msg msg){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            msgService.upd(msg);
            map.put("code",200);
        }catch (Exception e){
            map.put("code",500);
        }
        return map;
    }

    @RequestMapping("getWeather")
    public Map getWeather(String cityOrArea){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String weather = WeatherUtil.getWeather(cityOrArea);
            map.put("code",200);
            map.put("weather",weather);
        }catch (Exception e){
            map.put("code",500);
        }
        return map;
    }
    @RequestMapping("/sendMsg")
    public Map sendMsgToxixi(String text) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            redisServiceImpl.setValue("text2XiXi", text.replace("/r", ""));
            map.put("code",200);
        }catch (Exception e){
            map.put("code",500);
        }
        return map;
    }

    @RequestMapping("/redisTemp")
    public Map redisTemp(String text,boolean flag) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            redisServiceImpl.setValue("newFileName", text.replace("/r", ""));
            if(flag){//传过来的是给西西的照片
                redisServiceImpl.setValue("newFileName2XiXi", text.replace("/r", ""));
            }

            map.put("code",200);
        }catch (Exception e){
            map.put("code",500);
        }
        return map;
    }
    @RequestMapping("/getDay")
    public Map getDay() {
        Map<String, Object> map = new HashMap<String, Object>();
        String beginDate = "2021-07-24";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Long days = null;
        try {
            Date pastTime = format.parse(beginDate);
            Date currentTime = new Date();
            long diff = currentTime.getTime() - pastTime.getTime();
            days = diff / (1000 * 60 * 60 * 24);
            map.put("code",200);
            map.put("day","❤宝贝，今天是我们恋爱的第"+(days+1)+"天，爱你呦！！！❤");
            if((days+1)==99){
                map.put("day","❤宝贝，今天是我们恋爱的第"+(days+1)+"天，99999999！！！❤");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            map.put("code",500);
        }
        return map;
    }

}
