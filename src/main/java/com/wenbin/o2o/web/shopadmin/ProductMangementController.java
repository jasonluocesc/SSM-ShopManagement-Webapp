package com.wenbin.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenbin.o2o.dto.ImageHolder;
import com.wenbin.o2o.entity.Product;
import com.wenbin.o2o.service.ProductService;
import com.wenbin.o2o.util.CodeUtil;
import com.wenbin.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wenbin Luo @ Aalto University
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductMangementController {
    @Autowired
    private ProductService productService;

    private static final int IMAGE_MAX_COUNT = 6;

    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request)
    {
        Map<String,Object> modelMap = new HashMap<>();

        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request,"productStr");
        MultipartHttpServletRequest multipartHttpServletRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        // 若请求中包含文件流，则取出文件流
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            if(multipartResolver.isMultipart(request)){
                multipartHttpServletRequest=(MultipartHttpServletRequest)request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                for (int i=0;i<IMAGE_MAX_COUNT;i++){
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg"+i);
                    if(productImgFile!=null){
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                        productImgList.add(productImg);
                    }else {
                        break
                    }
                }
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","上传图片不能为空！");
                return modelMap;
            }
        } catch (IOException e) {


        }
        try {
            
        }
    }

}
