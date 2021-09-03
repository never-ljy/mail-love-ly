package com.gzt;

import com.alibaba.fastjson.JSONArray;
import com.gzt.mapper.LoveMapper;
import com.gzt.util.HttpClient;
import com.gzt.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.lang.model.element.VariableElement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
    @Test
    public void test(){
        String updateAssetUrl;
        //String updateAssetUrl = "https://restapi.amap.com/v3/weather/weatherInfo?key=40b42534bde2799c566fe84b3333f938&city=北京";


        StringBuffer strBuf = new StringBuffer();

        try{
            String city = URLEncoder.encode("海淀", "utf-8");
            updateAssetUrl = "https://restapi.amap.com/v3/weather/weatherInfo?extensions=base&key=40b42534bde2799c566fe84b3333f938&city="+city;
            URL url = new URL(updateAssetUrl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。
            String line = null;
            while ((line = reader.readLine()) != null)
                strBuf.append(line + " ");
            reader.close();
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println(strBuf.toString());

    }

}
