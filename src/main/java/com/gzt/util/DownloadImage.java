package com.gzt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class DownloadImage {
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub  
         download("https://1908a.oss-cn-beijing.aliyuncs.com/pic2xixi/1630480874434109.jpg", "https://1908a.oss-cn-beijing.aliyuncs.com/pic2xixi/1630480874434109.jpg","d:\\image\\");
    }  
      
    public static void download(String urlString, String filename,String savePath) throws Exception {
        final Logger logger = LoggerFactory.getLogger(DownloadImage.class);
        // 构造URL  
        URL url = new URL(urlString);  
        // 打开连接  
        URLConnection con = url.openConnection();  
        //设置请求超时为5s  
        con.setConnectTimeout(5*1000);  
        // 输入流  
        InputStream is = con.getInputStream();  
      
        // 1K的数据缓冲  
        byte[] bs = new byte[1024];  
        // 读取到的数据长度  
        int len;  
        // 输出的文件流  
       File sf=new File(savePath);  
       if(!sf.exists()){  
           sf.mkdirs();  
       }
        // 获取图片的扩展名
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        // 新的图片文件名 = 编号 +"."图片扩展名
        String newFileName = "love" + "." + extensionName;
        OutputStream os = new FileOutputStream(sf.getPath() + "/" + newFileName);
        logger.info("输出路径为：   ----    " + sf.getPath() + "/" + newFileName);
        // 开始读取  
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接  
        os.close();
        is.close();
    }   
  
}