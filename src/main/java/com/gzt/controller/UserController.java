package com.gzt.controller;

import com.gzt.model.User;
import com.gzt.service.MsgService;
import com.gzt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public Map login(String phone , String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //1.通过手机号找，若没有则返回错误提示
            User user = userService.findByPhone(phone);
            if(null ==user){
                map.put("code",400);
                return map;
            }
            //验证密码 MD5Util
            if(! password.equals(user.getPassword())){
                map.put("code",502);
                return map;
            }
            //密码正确
            map.put("code",200);
            map.put("profile",phone);
        }catch (Exception e){
            map.put("code",500);
        }
        return map;
    }
}
