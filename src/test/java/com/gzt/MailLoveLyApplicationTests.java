package com.gzt;

import com.gzt.mapper.LoveMapper;
import com.gzt.util.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest
class MailLoveLyApplicationTests {
    @Resource
    private LoveMapper loveMapper;

    @Test
    public void sendSimpleMail() throws Exception {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i <100; i++) {
            String message = HttpUtil.sendGet("https://api.mcloc.cn/love")+"--小芮";
            strings.add(message);
        }
        loveMapper.batchInsert(strings);


    }

}
