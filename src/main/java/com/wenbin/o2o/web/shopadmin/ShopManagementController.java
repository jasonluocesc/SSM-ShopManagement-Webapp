package com.wenbin.o2o.web.shopadmin;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenbin.o2o.dto.ShopExcution;
import com.wenbin.o2o.entity.PersonInfo;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.enums.ShopStateEnum;
import com.wenbin.o2o.exceptions.ShopOperationException;
import com.wenbin.o2o.service.ShopService;
import com.wenbin.o2o.util.HttpServletRequestUtil;
import com.wenbin.o2o.util.ImageUtil;
import com.wenbin.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {

        Map<String,Object> modelMap = new HashMap<String, Object>();

        //get and parse parameters
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop =null;
        try{
            shop=mapper.readValue(shopStr,Shop.class);
        } catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }

        CommonsMultipartFile shopImg=null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","上传文件不能为空");
            return modelMap;
        }
        //register shop

        if(shop!=null&&shopImg!=null){
            PersonInfo owner = new PersonInfo();
            //Session to do
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExcution se = null;
            try {
                se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                if(se.getState()==ShopStateEnum.CHECK.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success",false);
                    modelMap.put("errMsg",se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            } catch (ShopOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
            return modelMap;

        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }

    }

    /*private static void inputStreamToFile(InputStream inputStream,File file){
        FileOutputStream os = null;
        try{
            os=new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while(true){
                os.write(buffer,0,bytesRead);
            }
        }catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile产生异常"+e.getMessage());
        }finally {
            try {
                if(os!=null){
                    os.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (IOException e){
                throw new RuntimeException("inputStreamToFile关闭IO产生异常"+e.getMessage());
            }
        }
    }*/
}
