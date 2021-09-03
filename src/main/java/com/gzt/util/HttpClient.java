package com.gzt.util;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author by CLP
 * @Classname HttpClient
 * @Description
 * @Date 2020/8/25 13:51
 */
public class HttpClient {
    /**
     * post 请求
     * @param urlStr
     * @param params
     * @return
     * @throws Exception
     */
    public static String getURLByPost(String urlStr, String params) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(60000);
        conn.setReadTimeout(60000);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(conn.getOutputStream());
        } catch (Exception e) {
            Thread.sleep(50);
            printWriter = new PrintWriter(conn.getOutputStream());
        }
        printWriter.write(params);
        printWriter.flush();
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            try {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //try again
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            }
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                conn.disconnect();
                //Thread.sleep(10);
                if (in != null) {
                    in.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }
        return sb.toString();
    }
    public static String getURLByGet(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(60000);
        conn.setReadTimeout(60000);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(conn.getOutputStream());
        } catch (Exception e) {
            Thread.sleep(50);
            printWriter = new PrintWriter(conn.getOutputStream());
        }
        //printWriter.write(params);
        //printWriter.flush();
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            try {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //try again
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            }
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                conn.disconnect();
                //Thread.sleep(10);
                if (in != null) {
                    in.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }
        return sb.toString();
    }

    public static String HttpPostWithJson(String url, String json) {
        String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }
}
