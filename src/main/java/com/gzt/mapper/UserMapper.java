package com.gzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gzt.model.Msg;
import com.gzt.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
