package com.wenbin.o2o.util;

import com.wenbin.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * @author Wenbin Luo @ Aalto University
 */
public class ImageUtil {

    private static String separator = System.getProperty("file.separator");
    private static String basePath ="E:/Projects/o2oimage".replace("/",separator);
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static String generateNormalImg(ImageHolder thumbnail,String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is: "+relativeAddr);
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("current completeAddr is: "+PathUtil.getImgBasePath()+relativeAddr);

        try{
            Thumbnails.of(thumbnail.getImage()).size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败："+e.toString());
        }
        return relativeAddr;
    }

    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;

        logger.debug("current relative address is : "+relativeAddr);

        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);

        logger.debug("current complete address is : "+PathUtil.getImgBasePath()+relativeAddr);
        try{
            Thumbnails.of(thumbnail.getImage()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath +"/watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        }catch (IOException e){
            e.printStackTrace();
        }
        return relativeAddr;
    }


    public static String getRandomFileName() {
        int ranNum = random.nextInt(89999)+10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr+ranNum;
    }

    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdir();
        }
    }


    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        //System.out.println(PathUtil.getImgBasePath()+storePath);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File files[] = fileOrPath.listFiles();
                for(int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
