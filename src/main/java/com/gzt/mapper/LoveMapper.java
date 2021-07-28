package com.gzt.mapper;

import com.gzt.model.Msg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoveMapper {
    //List<String> getMessage();
    boolean delMsg(int id);
    void batchInsert(List<String> strings);
    Msg getLastOne();
    Msg getFirstOne();
}
