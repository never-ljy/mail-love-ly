package com.gzt.service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzt.mapper.LoveMapper;
import com.gzt.model.Msg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsgServiceImpl implements MsgService{
    @Resource
    private LoveMapper loveMapper;
    @Override
    public Map<String, Object> queryList(int start, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageHelper.startPage(start, pageSize);
        List<Msg> msgList = loveMapper.queryList();
        map.put("msgList",msgList);
        map.put("total",((Page) msgList).getTotal());
        return map;
    }

    @Override
    public void del(Msg msg) {
        loveMapper.delMsg(msg.getId());
    }

    @Override
    public void upd(Msg msg) {
        loveMapper.updateById(msg);
    }
}
