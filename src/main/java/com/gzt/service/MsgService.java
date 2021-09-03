package com.gzt.service;

import com.gzt.model.Msg;

import java.util.Map;

public interface MsgService {
    Map<String, Object> queryList(int start, int pageSize);

    void del(Msg msg);

    void upd(Msg msg);
}
