package com.gzt.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.gzt.redis.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisServiceImpl redisServiceImpl;

    @RequestMapping("/upload")
    public String upload(ModelMap map) {
        getDay(map);
        return "upload";
    }

    private void getDay(ModelMap map) {
        String beginDate = "2021-07-24";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Long days = null;
        try {
            Date pastTime = format.parse(beginDate);
            Date currentTime = new Date();
            long diff = currentTime.getTime() - pastTime.getTime();
            days = diff / (1000 * 60 * 60 * 24);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        map.addAttribute("day","❤宝贝，今天是我们恋爱的第"+(days+1)+"天，每一天都要很开心喔！！！❤");
    }

    @RequestMapping("/uploadToXiXi")
    public String uploadToXiXi(ModelMap map) {
        getDay(map);
        return "uploadxixi";
    }

    @RequestMapping("/uploadPicture")
    public String uploadPicture(ModelMap map,MultipartFile file) {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G8Ta5ZqMhCqpVq14Snb";
        String accessKeySecret = "R6TB531r8FFsmwEQhYyQTanJHFk32f";
        String prefixUrl = "https://1908a.oss-cn-beijing.aliyuncs.com/";


        String originalFilename = file.getOriginalFilename();
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        InputStream inputStream = null;

        try {
            inputStream = file.getInputStream();

            // 创建OSSClient实例。
            OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            // 创建PutObjectRequest对象。
            //String content = "Hello OSS";
            // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
            PutObjectRequest putObjectRequest = new PutObjectRequest("1908a", newFileName, inputStream);
            redisServiceImpl.setValue("newFileName",newFileName);
            logger.info("上传到阿里云的文件名为："+newFileName);
            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传字符串。
            ossClient.putObject(putObjectRequest);
            // 关闭OSSClient。
            ossClient.shutdown();
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/upload2xixi")
    public String upload2xixi(ModelMap map,MultipartFile file,String text) {

        redisServiceImpl.setValue("text2XiXi", text.replace("/r",""));
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G8Ta5ZqMhCqpVq14Snb";
        String accessKeySecret = "R6TB531r8FFsmwEQhYyQTanJHFk32f";
        String prefixUrl = "https://1908a.oss-cn-beijing.aliyuncs.com/";


        if (file.getOriginalFilename() != "") {
            String originalFilename = file.getOriginalFilename();
            String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
            InputStream inputStream = null;

            try {
                inputStream = file.getInputStream();

                // 创建OSSClient实例。
                OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

                // 创建PutObjectRequest对象。
                //String content = "Hello OSS";
                // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
                PutObjectRequest putObjectRequest = new PutObjectRequest("1908a", newFileName, inputStream);
                redisServiceImpl.setValue("newFileName2XiXi", newFileName);
                logger.info("上传到阿里云的文件名为：" + newFileName);
                // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
                // ObjectMetadata metadata = new ObjectMetadata();
                // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
                // metadata.setObjectAcl(CannedAccessControlList.Private);
                // putObjectRequest.setMetadata(metadata);

                // 上传字符串。
                ossClient.putObject(putObjectRequest);
                // 关闭OSSClient。
                ossClient.shutdown();
                return "success1";
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "error";
        }
        return "error";
    }


}