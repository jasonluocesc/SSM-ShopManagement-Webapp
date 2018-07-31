package com.wenbin.o2o.util;

/**
 * @author Wenbin Luo @ Aalto University
 */
public class PathUtil {

    private static String seperator = System.getProperty("file.separator");

    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath="E:/Projects/o2oimage/";
        }else{
            basePath="/Users/baidu/work/image/";
        }
        basePath = basePath.replace("/",seperator);
        return basePath;
    }

    public static String getShopImagePath(long shopId){
        String imagePath = "upload/item/shop/"+shopId+"/";
        return imagePath.replace("/",seperator);
    }
}
