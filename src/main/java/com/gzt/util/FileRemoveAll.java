package com.gzt.util;

import java.io.File;

public class FileRemoveAll {
   /* public static void main(String[] args){
        File file = new File("文件路径");//创建文件类，指定要删除的文件夹路径
        remove(file);
        file.delete();//删除根目录
        if (!file.exists()){
            System.out.println("删除成功");
        }
    }*/
    public static void remove(File file){
        File[] files = file.listFiles();//将file子目录及子文件放进文件数组
        if (files!=null){//如果包含文件进行删除操作
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()){//删除子文件
                    files[i].delete();
                }else if(files[i].isDirectory()){//通过递归方法删除子目录的文件
                    remove(files[i]);
                }
                files[i].delete();//删除子目录
            }
        }

    }
}