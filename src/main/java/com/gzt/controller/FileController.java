package com.gzt.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.gzt.redis.RedisServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
@Controller
@CrossOrigin
@RequestMapping("file")
public class FileController {
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAI4G8Ta5ZqMhCqpVq14Snb";
    String accessKeySecret = "R6TB531r8FFsmwEQhYyQTanJHFk32f";
    String prefixUrl = "https://1908a.oss-cn-beijing.aliyuncs.com/";
    String bucketName="1908a";
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

            //todo
            ossClient.deleteObject("1908a", redisServiceImpl.getValue("newFileName").toString());
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
    /**
     * 获取文件夹
     *
     * @param fileName
     * @return
     */
    @RequestMapping("/fileFolder")
    public  List<String> fileFolder(String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");
        // 设置prefix参数来获取fun目录下的所有文件。
        if (StringUtils.isNotBlank(fileName)) {
            listObjectsRequest.setPrefix(fileName + "/");
        }
        // 列出文件
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有commonPrefix
        List<String> list = new ArrayList<>();
        for (String commonPrefix : listing.getCommonPrefixes()) {
            String newCommonPrefix = commonPrefix.substring(0, commonPrefix.length() - 1);
            String[] s = newCommonPrefix.split("/");
            list.add(s[1]);
        }
        // 关闭OSSClient
        ossClient.shutdown();
        return list;
    }

    /**
     * 列举文件下所有的文件url信息
     */
    @RequestMapping("/listFile")
    @ResponseBody
    public  List<String> listFile(String fileHost) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 构造ListObjectsRequest请求
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

        // 设置prefix参数来获取fun目录下的所有文件。
        listObjectsRequest.setPrefix(fileHost + "/");
        // 列出文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件。
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listing.getObjectSummaries().size(); i++) {
            if (i == 0) {
                continue;
            }
            // String prefixUrl = "https://1908a.oss-cn-beijing.aliyuncs.com/";
            //endpoint.substring(6)
            String FILE_URL = "https://" + bucketName + "." + endpoint.substring(7) + "/" + listing.getObjectSummaries().get(i).getKey();
            list.add(FILE_URL);
        }
        //https://1908a.oss-cn-beijing.aliyuncs.com/banners/10c419f76b8565f9204aa71d70c6937.jpg
        // 关闭OSSClient。
        ossClient.shutdown();
        return list;
    }

    @RequestMapping("/delList")
    public void delList(String bannerList) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 构造ListObjectsRequest请求

        List<String> list = JSONObject.parseObject(bannerList, List.class);

        for (String filePath : list) {
            //https://1908a.oss-cn-beijing.aliyuncs.com/banners/10c419f76b8565f9204aa71d70c6937.jpg
            String substring = filePath.substring(42);//    banners/10c419f76b8565f9204aa71d70c6937.jpg
            boolean exist = ossClient.doesObjectExist(bucketName, substring);
            if (!exist) {
                continue;
            }
            ossClient.deleteObject(bucketName, substring);
        }
        ossClient.shutdown();
    }
}