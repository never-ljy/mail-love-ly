package com.gzt.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_love_msg")
public class Msg {
    public int id ;
    public String msg ;
}
