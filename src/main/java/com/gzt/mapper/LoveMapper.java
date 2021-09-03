package com.gzt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gzt.model.Msg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoveMapper extends BaseMapper<Msg> {
    //List<String> getMessage();
    boolean delMsg(int id);
    void batchInsert(List<String> strings);
    Msg getLastOne();
    Msg getFirstOne();

    List<Msg> queryList();
}
