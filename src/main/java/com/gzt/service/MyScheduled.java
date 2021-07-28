package com.gzt.service;

import com.alibaba.fastjson.JSONObject;
import com.gzt.mapper.LoveMapper;

import com.gzt.model.Msg;
import com.gzt.redis.RedisServiceImpl;
import com.gzt.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component
public class MyScheduled {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LoveMapper loveMapper;

    @Autowired
    private RedisServiceImpl redisServiceImpl;
    //芮宝贝邮箱号
    private String sheMail = "2500985202@qq.com";

    //递归方法获取不重复的一条msg
    public Msg getDistinct(){
        Msg msg = loveMapper.getFirstOne();
        Object value = redisServiceImpl.getValue(msg.getMsg());
        if (("").equals(redisServiceImpl.getValue(msg.getMsg())) || null == redisServiceImpl.getValue(msg.getMsg())) {
            return msg;
        }
        loveMapper.delMsg(msg.getId());
        return getDistinct();
    }
    @Scheduled(cron ="0 08 15 * * *")
    public void prepareMsg(){
        Msg msg = getDistinct();
        String s = JSONObject.toJSONString(msg);
        redisServiceImpl.setValue("pre",s);
        logger.info("存入redis成功 pre：---------"+s);
    }
    /*定时执行任务方法 每天21点57执行该任务*/
    @Scheduled(cron ="0 57 21 * * *")
    public void loveForever(){
        //发送成功标识
        boolean flag;
        //Msg msg = getDistinct();
        Msg msg = JSONObject.parseObject(redisServiceImpl.getValue("pre").toString(), Msg.class);
        logger.info("发的消息是：---------"+msg.getMsg()+msg.getId());

        String distName="/opt/pic/";
        File file = new File(distName);
        String[] list = file.list();
        String fileName = distName + list[0];
        logger.info("fileName为：------------"+fileName);
        logger.info("第1次尝试发送附件邮件");
        flag = MailUtil.sendMailWithAttachment(msg.getMsg(),sheMail,fileName);
        //flag = MailUtil.sendMailWithAttachment(msg.getMsg(),"1249094460@qq.com","C:\\Users\\Admin\\Desktop\\love.jpg");
        if (!flag) {
            try {
                logger.info("第2次尝试发送附件邮件");
                flag = MailUtil.sendMailWithAttachment(msg.getMsg(), sheMail, "/opt/pic/love.jpg");
                //mailService.sendAttachmentsMail(sheMail, "来自西西的消息！❤", s, "/opt/pic/love.jpg");
            } catch (Exception e) {
                logger.info("第3次尝试发送  邮件");
                MailUtil.sendMail("来自西西的消息！❤--啊哦附件邮件发送失败了哦", msg.getMsg(), sheMail);
                //mailService.sendSimpleMail(sheMail, "来自西西的消息！❤", s);
            }
        }
        if (flag) {
            logger.info("第1次发送附件邮件成功");
            redisServiceImpl.setValue(msg.getMsg(), msg.getMsg());
            loveMapper.delMsg(msg.getId());
        }
    }

    /*定时执行任务方法 每天21点57执行该任务
    * 当redis里有东西的时候会发送邮件，没有就不发送
    * */
    @Scheduled(cron ="0 57 21 * * *")
    public void send2xixi(){
        if(null!=redisServiceImpl.getValue("text2XiXi") && ""!= redisServiceImpl.getValue("text2XiXi")){
            //发送成功标识
            boolean flag;
            String msg = redisServiceImpl.getValue("text2XiXi").toString();
            msg = StringUtil.replaceBlank(msg);
            //String newFileName2XiXi = redisServiceImpl.getValue("newFileName2XiXi").toString();
            String distName="/opt/picXi/";
            //String distName="D:\\upload";
            String fileName = distName + "love.jpg";
            //String fileName = distName + "\\a.jpg";
            logger.info("fileName为：------------"+fileName);
            logger.info("第1次尝试发送附件邮件");
                flag = MailUtil.sendMailWithAttachmentToXiXi(msg, Const.email.MAIL_FROM,fileName);
            if (flag) {
                logger.info("第1次发送附件邮件成功");
                redisServiceImpl.delValue("text2XiXi");
                redisServiceImpl.delValue("newFileName2XiXi");
            }
        }

    }

    /**、
     * 把oss的照片发到opt文件夹里
     * zr___xixi
     */
    @Scheduled(cron ="0 30 21 * * *")
    public void resetPic() {
        try {
            String distName="/opt/picXi/";
            File file = new File(distName);
            FileRemoveAll fileRemoveAll = new FileRemoveAll();
            if (file.listFiles().length != 0){
                logger.info("将/opt/picXi文件夹清空");
                fileRemoveAll.remove(file);
            }
            String newFileName2XiXi = redisServiceImpl.getValue("newFileName2XiXi").toString();
            logger.info("上传到阿里云的文件名为："+newFileName2XiXi);
            DownloadImage.download("https://1908a.oss-cn-beijing.aliyuncs.com/"+newFileName2XiXi, newFileName2XiXi,"/opt/picXi/");
            logger.info("将oss图片下载到服务器success");
        }catch (Exception e){
            logger.info("将oss图片下载到服务器失败");
        }

    }

    /**、
     * 把oss的照片发到opt文件夹里
     * ljy
     */
    @Scheduled(cron ="0 30 17 * * *")
    public void resetPic2xixi() {
        try {
            String distName="/opt/pic/";
            File file = new File(distName);
            FileRemoveAll fileRemoveAll = new FileRemoveAll();
            if (file.listFiles().length != 0){
                logger.info("将/opt/pic文件夹清空");
                fileRemoveAll.remove(file);
            }
            String newFileName = redisServiceImpl.getValue("newFileName").toString();
            logger.info("上传到阿里云的文件名为："+newFileName);
            DownloadImage.download("https://1908a.oss-cn-beijing.aliyuncs.com/"+newFileName, newFileName,"/opt/pic/");
            logger.info("将oss图片下载到服务器success");
        }catch (Exception e){
            logger.info("将oss图片下载到服务器失败");
        }

    }

    /*@Scheduled(cron ="0 0 14 * * *")
    public void resetPicLocal() {
        try {
            String distName="D:\\upload";
            File file = new File(distName);
            FileRemoveAll fileRemoveAll = new FileRemoveAll();
            if (file.listFiles().length != 0){
                logger.info("将/opt/pic文件夹清空");
                fileRemoveAll.remove(file);
            }
            String newFileName = redisServiceImpl.getValue("newFileName").toString();
            logger.info("上传到阿里云的文件名为："+newFileName);
            DownloadImage.download("https://1908a.oss-cn-beijing.aliyuncs.com/"+newFileName, newFileName,"D:\\upload");
            logger.info("将oss图片下载到服务器success");
        }catch (Exception e){
            logger.info("将oss图片下载到服务器失败");
        }

    }*/

    /*@Scheduled(cron ="0 30 10 * * *")
    public void test(){
        //String message = sendMessage.getOneS();
        String s = loveMapper.getLastOne();
        boolean flag = false;

        String distName="/opt/pic/";
        File file = new File(distName);
        String[] list = file.list();

        String fileName = distName + list[0];
        logger.info("fileName为：------------"+fileName);
        logger.info("第1次尝试发送附件邮件");
        flag = MailUtil.sendMailWithAttachment(s,"1249094460@qq.com",fileName);
        //"1249094460@qq.com", "来自西西的消息！❤", "s", "C:\\Users\\Admin\\Desktop\\a.mp4"
        //flag = MailUtil.sendMailWithAttachment(s,"1249094460@qq.com","C:\\Users\\Admin\\Desktop\\a.mp4");
        logger.info("第1次发送附件邮件成功");
        if (flag) {
            loveMapper.delMsg(s);
        }else{
            try{
                MailUtil.sendMailWithAttachment(s,"1249094460@qq.com","/opt/pic/love.jpg");
                //mailService.sendAttachmentsMail(sheMail, "来自西西的消息！❤", s, "/opt/pic/love.jpg");
            } catch (Exception e){
                MailUtil.sendMail("来自西西的消息！❤", s ,"1249094460@qq.com");
                //mailService.sendSimpleMail(sheMail, "来自西西的消息！❤", s);
            }

        }
    }*/


}