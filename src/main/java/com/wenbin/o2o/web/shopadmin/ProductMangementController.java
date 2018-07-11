package com.wenbin.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenbin.o2o.dto.ImageHolder;
import com.wenbin.o2o.dto.ProductExecution;
import com.wenbin.o2o.entity.Product;
import com.wenbin.o2o.entity.ProductCategory;
import com.wenbin.o2o.entity.Shop;
import com.wenbin.o2o.enums.ProductStateEnum;
import com.wenbin.o2o.exceptions.ProductOperationException;
import com.wenbin.o2o.service.ProductCategoryService;
import com.wenbin.o2o.service.ProductService;
import com.wenbin.o2o.util.CodeUtil;
import com.wenbin.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private ProductCategoryService productCategoryService;

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
                handleImage(productImgList, multipartHttpServletRequest);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","上传图片不能为空！");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;

        }
        try {
            // 尝试获取前端传过来的表单string流并实体化
            product=mapper.readValue(productStr,Product.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }

        if(product!=null&&thumbnail!=null&&productImgList.size()>0){
            try{
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                // 执行添加操作
                ProductExecution pe = productService.addProduct(product,thumbnail,productImgList);
                if(pe.getState()==ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pe.getStateInfo());
                }
            }catch (ProductOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductlistbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductListByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if((pageIndex>-1)&&(pageSize>-1)&&(currentShop!=null)&&(currentShop.getShopId()!=null)){
            // 获取传入的需要检索的条件，包括是否需要从某个商品类别模糊查找商品
            long producCategoryId = HttpServletRequestUtil.getLong(request,"productCategoryId");
            String productName = HttpServletRequestUtil.getString(request,"productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(),producCategoryId,productName);
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "无效的数据页数或店铺id");
        }
        return modelMap;
    }

    private Product compactProductCondition(long shopId, long producCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (producCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(producCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if(productName!=null){
            productCondition.setProductName(productName);
        }
        return productCondition;
    }


    @RequestMapping(value = "/getproductbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getProductById(@RequestParam Long productId){

        Map<String, Object> modelMap = new HashMap<>();
        if(productId>-1){
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product",product);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入商品id！");
        }

        return modelMap;
    }

    @RequestMapping(value = "/modifyproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,"statusChange");

        if(!statusChange&&!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码！");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try{
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail.jpg");
                if(thumbnailFile!=null){
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
                }
                handleImage(productImgList, multipartRequest);
            }
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        try{
            String productStr = HttpServletRequestUtil.getString(request,"productStr");
            product = mapper.readValue(productStr,Product.class);
        }catch(Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }

        if(product!=null){
            try{
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.modifyProduct(product,thumbnail,productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;

    }

    private void handleImage(List<ImageHolder> productImgList, MultipartHttpServletRequest multipartRequest) throws IOException {
        for(int i=0;i<IMAGE_MAX_COUNT;i++){
            CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartRequest.getFile("productImg"+i);
            if(productImgFile!=null){
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                productImgList.add(productImg);
            }else {
                break;
            }
        }
    }

}
